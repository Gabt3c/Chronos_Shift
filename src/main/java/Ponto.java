import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Ponto implements RegistroJornada {
    private static final DateTimeFormatter FORMATADOR_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String dataReg;
    private String horarioEntrada;
    private String horarioSaida;
    private String pausaInicio;
    private String pausaFim;
    private String justificativa;

    private static List <Ponto> historicoPontos = new ArrayList<>();

    // Identificação do ponto e contrato usado para guardar o histórico em arquivo.
    private long idRegistro;
    private int codigoColaborador;
    private PersistenciaPonto persistencia;

    // Mantém a criação antiga de Ponto para os exemplos já existentes.
    public Ponto() {
        this(0, new PersistenciaPontoJson(java.nio.file.Path.of("data", "registros-ponto.json")));
    }

    // Recebe a identificação do colaborador e permite trocar a persistência nos testes.
    public Ponto(int codigoColaborador, PersistenciaPonto persistencia) {
        if (codigoColaborador < 0 || persistencia == null) {
            throw new IllegalArgumentException("Informe um código válido e a persistência.");
        }
        this.codigoColaborador = codigoColaborador;
        this.persistencia = persistencia;

        // Ao reabrir o programa, retoma o último ponto de hoje do mesmo colaborador.
        String hoje = LocalDate.now().format(FORMATADOR_DATA);
        for (RegistroPonto registro : consultarHistoricoPontos()) {
            if (hoje.equals(registro.getDataReg()) && registro.getId() > this.idRegistro) {
                this.idRegistro = registro.getId();
                this.dataReg = registro.getDataReg();
                this.horarioEntrada = registro.getHorarioEntrada();
                this.horarioSaida = registro.getHorarioSaida();
                this.pausaInicio = registro.getPausaInicio();
                this.pausaFim = registro.getPausaFim();
                this.justificativa = registro.getJustificativa();
            }
        }
    }

    // Recupera do arquivo apenas os registros vinculados a este colaborador.
    public List<RegistroPonto> consultarHistoricoPontos() {
        try {
            List<RegistroPonto> registros = new ArrayList<>();
            for (RegistroPonto registro : this.persistencia.carregarRegistros()) {
                if (registro.getCodigoColaborador() == this.codigoColaborador) {
                    registros.add(registro);
                }
            }
            return registros;
        } catch (java.io.IOException erro) {
            throw new java.io.UncheckedIOException("Não foi possível ler o histórico de pontos.", erro);
        }
    }

    // Converte os campos internos em dados simples antes de gravar no JSON.
    public void salvarPonto() {
        salvarPonto("AJUSTE");
    }

    // Cada ação cria um evento independente: o ID muda e o colaborador permanece.
    private void salvarPonto(String tipo) {
        if (this.dataReg == null || this.dataReg.isBlank()) {
            this.dataReg = LocalDate.now().format(FORMATADOR_DATA);
        }
        RegistroPonto registro = new RegistroPonto();
        registro.setId(0);
        registro.setTipo(tipo);
        registro.setDataHora(java.time.LocalDateTime.now());
        registro.setCodigoColaborador(this.codigoColaborador);
        registro.setDataReg(this.dataReg);
        registro.setHorarioEntrada(this.horarioEntrada);
        registro.setHorarioSaida(this.horarioSaida);
        registro.setPausaInicio(this.pausaInicio);
        registro.setPausaFim(this.pausaFim);
        registro.setJustificativa(this.justificativa);
        try {
            this.idRegistro = this.persistencia.salvarRegistro(registro);
        } catch (java.io.IOException erro) {
            throw new java.io.UncheckedIOException("Não foi possível salvar o registro de ponto.", erro);
        }

        // Preserva a lista em memória utilizada pelo código original.

        if (!historicoPontos.contains(this)) {
            historicoPontos.add(this);
        }
    }
    public static List <Ponto> gethistoricoPontos() {
        return historicoPontos;
    }

    private String obterHoraAtualFormatada() {
        return LocalTime.now().format(FORMATADOR_HORA);
    }

    // Se o programa continuar aberto em outro dia, inicia um ponto separado.
    private void prepararDataAtual() {
        String hoje = LocalDate.now().format(FORMATADOR_DATA);
        if (this.dataReg != null && !hoje.equals(this.dataReg)) {
            this.idRegistro = 0;
            this.horarioEntrada = null;
            this.horarioSaida = null;
            this.pausaInicio = null;
            this.pausaFim = null;
            this.justificativa = null;
        }
        this.dataReg = hoje;
    }

    public void registrarEntrada() {
        prepararDataAtual();
        if (this.dataReg == null || this.dataReg.isBlank()) {
            this.dataReg = LocalDate.now().format(FORMATADOR_DATA);
        }
        this.horarioEntrada = obterHoraAtualFormatada();
        salvarPonto("ENTRADA");
    }

    public void registrarSaida() {
        prepararDataAtual();
        this.horarioSaida = obterHoraAtualFormatada();
        salvarPonto("SAIDA");
    }

    public void registrarInicioPausa() {
        prepararDataAtual();
        this.pausaInicio = obterHoraAtualFormatada();
        salvarPonto("INICIO_PAUSA");
    }

    public void registrarFimPausa() {
        prepararDataAtual();
        this.pausaFim = obterHoraAtualFormatada();
        salvarPonto("FIM_PAUSA");
    }

    public void registrarPausa() {
        prepararDataAtual();
        if (this.pausaInicio == null || this.pausaInicio.isBlank()) {
            registrarInicioPausa();
        } else {
            registrarFimPausa();
        }
    }

    // 1. Assinatura definida pelo Arthur (recebe apenas a justificativa)
    public void justificarSugestao(String justificativa) {
        if (justificativa != null && !justificativa.isBlank()) {
            this.justificativa = justificativa;
            salvarPonto();
        }
    }

    // 2. Assinatura criada pela Mariana (atualiza os 4 horários)
    public void ajustarPonto(String novaEntrada, String novaSaida, String novaPausaInicio, String novaPausaFim) {
        if (novaEntrada != null && !novaEntrada.isBlank()) {
            this.horarioEntrada = novaEntrada;
        }
        if (novaSaida != null && !novaSaida.isBlank()) {
            this.horarioSaida = novaSaida;
        }
        if (novaPausaInicio != null && !novaPausaInicio.isBlank()) {
            this.pausaInicio = novaPausaInicio;
        }
        if (novaPausaFim != null && !novaPausaFim.isBlank()) {
            this.pausaFim = novaPausaFim;
        }
        salvarPonto();
    }

    // 3. Assinatura completa (atualiza os 4 horários e regista a justificativa)
    public void ajustarPonto(String novaEntrada, String novaSaida, String novaPausaInicio, String novaPausaFim, String justificativa) {
        // Guarda a justificativa antes de emitir um único evento de ajuste.
        if (justificativa != null && !justificativa.isBlank()) {
            this.justificativa = justificativa;
        }
        ajustarPonto(novaEntrada, novaSaida, novaPausaInicio, novaPausaFim);
    }

    public String getDataReg() {
        return "Data do registro" + this.dataReg;
    }

    public void setDataReg(String dataReg) {
        if (!java.util.Objects.equals(this.dataReg, dataReg)) {
            this.idRegistro = 0;
        }
        this.dataReg = dataReg;
        salvarPonto();
    }

    public String getHorarioEntrada() {
        return "Horário de entrada: " + this.horarioEntrada;
    }

    public String getHorarioSaida() {
        return "Horário de saída: " + this.horarioSaida;
    }

    public String getPausaInicio() {
        return "Horário de início da pausa: " + this.pausaInicio;
    }

    public String getPausaFim() {
        return "Horário de fim da pausa: " + this.pausaFim;
    }

    public String getJustificativa() {
        return this.justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
        salvarPonto();
    }
}
