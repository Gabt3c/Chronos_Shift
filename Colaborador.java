
public class Colaborador {
    // Atributos definidos no diagrama UML.
    private String nome;
    private String cpfColaborador;
    private int codigoColaborador;
    private Ponto ponto;

    // Construtor para identificar o colaborador.
    public Colaborador(String nome, String cpfColaborador, int codigoColaborador) {
        this.nome = nome;
        this.cpfColaborador = cpfColaborador;
        this.codigoColaborador = codigoColaborador;
        this.ponto= new Ponto();
    }

    //Registros de ponto
    public void registrarEntrada(){
        this.ponto.registrarEntrada();
    }

    public void registrarSaida() {
        this.ponto.registrarSaida();
    }

    public void registrarInicioPausa(){
        this.ponto.registrarInicioPausa();
    }

    public void registrarFimPausa(){
        this.ponto.registrarFimPausa();
    }

    public void registrarPausa(){
        this.ponto.registrarFimPausa();
    }

    public void ajustarPonto(String novaEntrada, String novaSaida, String novaPausaInicio, String novaPausaFim, String justificativa){
        this.ponto.ajustarPonto(novaEntrada, novaSaida, novaPausaInicio, novaPausaFim, justificativa);
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
