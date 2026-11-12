
public class Colaborador {
    // Atributos definidos no diagrama UML.
    private String nome;
    private String cpfColaborador;
    private int codigoColaborador;
    private Ponto ponto;

    // Construtor para identificar o colaborador.
    public Colaborador(String nome, String cpfColaborador, int codigoColaborador) {
        this(nome, cpfColaborador, codigoColaborador,
            new PersistenciaPontoJson(java.nio.file.Path.of("data", "registros-ponto.json")));
    }

    // Permite usar outra persistência sem mudar a forma de registrar o ponto.
    public Colaborador(String nome, String cpfColaborador, int codigoColaborador,
            PersistenciaPonto persistencia) {
        this.nome = nome;
        this.cpfColaborador = cpfColaborador;
        this.codigoColaborador = codigoColaborador;
        this.ponto = new Ponto(codigoColaborador, persistencia);
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
        this.ponto.registrarPausa();
    }

    public void ajustarPonto(String novaEntrada, String novaSaida, String novaPausaInicio, String novaPausaFim, String justificativa){
        this.ponto.ajustarPonto(novaEntrada, novaSaida, novaPausaInicio, novaPausaFim, justificativa);
    }

    public void setDataReg(String dataReg){
        this.ponto.setDataReg(dataReg);
    }

    // Consulta o histórico persistido do colaborador, inclusive após reiniciar.
    public java.util.List<RegistroPonto> consultarHistoricoPontos() {
        return this.ponto.consultarHistoricoPontos();
    }

    //Retornos de ponto
    public String getDataReg() {
        return this.ponto.getDataReg();
    }

    public String getHorarioEntrada() {
        return this.ponto.getHorarioEntrada();
    }

    public String getHorarioSaida() {
        return this.ponto.getHorarioSaida();
    }

    public String getPausaInicio() {
        return this.ponto.getPausaInicio();
    }

    public String getPausaFim() {
        return this.ponto.getPausaFim();
    }

    public String getJustificativa() {
        return this.ponto.getJustificativa();
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
