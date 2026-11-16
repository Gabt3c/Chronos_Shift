public class Colaborador {
    private String nome;
    private String cpfColaborador;
    private int codigoColaborador;
    private Ponto ponto;

    public Colaborador(String nome, String cpfColaborador, int codigoColaborador) {
        this.nome = nome;
        this.cpfColaborador = cpfColaborador;
        this.codigoColaborador = codigoColaborador;
    }

    public void registraPonto(Ponto ponto) {

    }

    public void solicitarFolga(Folga folga) {

    }

    public void solicitarAjustePonto(Ponto ponto) {

    }

    public void solicitarTrocaPlantao(Plantao plantao) {
        
    }

    public double consultarSaldoHoras() { 
        return 0.0; 
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfColaborador() {
        return cpfColaborador;
    }

    public void setCpfColaborador(String cpfColaborador) {
        this.cpfColaborador = cpfColaborador;
    }

    public int getCodigo() {
        return this.codigoColaborador;
    }

    public void setCodigoColaborador(int codigoColaborador) {
        this.codigoColaborador = codigoColaborador;
    }

    public Ponto getPonto() {
        return ponto;
    }

    public void setPonto(Ponto ponto) {
        this.ponto = ponto;
    }
}
