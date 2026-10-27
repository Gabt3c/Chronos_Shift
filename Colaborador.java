public class Colaborador {
    // Atributos definidos no diagrama UML.
    private String nome;
    private String cpfColaborador;
    private int codigoColaborador;

    // Atributo adicional para guardar a solicitação até a análise do gestor.
    private Ponto pontoAguardandoAjuste;

    // Construtor para identificar o colaborador.
    public Colaborador(String nome, String cpfColaborador, int codigoColaborador) {
        this.nome = nome;
        this.cpfColaborador = cpfColaborador;
        this.codigoColaborador = codigoColaborador;
    }

    // Registra a entrada no objeto Ponto recebido.
    public void registrarPonto(Ponto ponto) {
        // Impede o registro quando não há um ponto informado.
        if (ponto == null) {
            System.out.println("Não foi possível registrar: ponto não informado.");
            return;
        }

        // Evita registrar uma segunda entrada no mesmo ponto.
        if (ponto.getHorarioEntrada() != null) {
            System.out.println("A entrada deste ponto já foi registrada.");
            return;
        }

        // Aproveita o método que já existe na classe Ponto.
        ponto.registrarEntrada();
        System.out.println("Entrada registrada para " + nome + ".");
    }

    // Solicita uma revisão do ponto, sem alterar os horários automaticamente.
    public void solicitarAjustePonto(Ponto ponto) {
        // Confere se existe um ponto e uma justificativa preenchida.
        if (ponto == null || ponto.getJustificativa() == null || ponto.getJustificativa().isBlank()) {
            System.out.println("Informe um ponto com justificativa para solicitar o ajuste.");
            return;
        }

        // Guarda a solicitação para futura análise pelo gestor.
        this.pontoAguardandoAjuste = ponto;
        System.out.println("Solicitação de ajuste registrada para " + nome + ".");
    }

    // Permite consultar o ponto pendente de ajuste.
    public Ponto getPontoAguardandoAjuste() {
        return pontoAguardandoAjuste;
    }

    // Métodos de leitura dos dados do colaborador.
    public String getNome() {
        return nome;
    }

    public String getCpfColaborador() {
        return cpfColaborador;
    }

    public int getCodigoColaborador() {
        return codigoColaborador;
    }
}
