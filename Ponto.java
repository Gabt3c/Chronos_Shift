import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Ponto {
    private static final DateTimeFormatter FORMATADOR_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String dataReg;
    private String horarioEntrada;
    private String horarioSaida;
    private String pausaInicio;
    private String pausaFim;
    private String justificativa;

    private static List <Ponto> historicoPontos = new ArrayList<>();

    public void salvarPonto() {
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

    public void registrarEntrada() {
        if (this.dataReg == null || this.dataReg.isBlank()) {
            this.dataReg = LocalDate.now().format(FORMATADOR_DATA);
        }
        this.horarioEntrada = obterHoraAtualFormatada();
        salvarPonto();
    }

    public void registrarSaida() {
        this.horarioSaida = obterHoraAtualFormatada();
        salvarPonto();
    }

    public void registrarInicioPausa() {
        this.pausaInicio = obterHoraAtualFormatada();
        salvarPonto();
    }

    public void registrarFimPausa() {
        this.pausaFim = obterHoraAtualFormatada();
        salvarPonto();
    }

    public void registrarPausa() {
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
        justificarSugestao(justificativa);
        ajustarPonto(novaEntrada, novaSaida, novaPausaInicio, novaPausaFim);
    }

    public String getDataReg() {
        return "Data do registro" + this.dataReg;
    }

    public void setDataReg(String dataReg) {
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
    }
}
