public class Escala {
    private int codigoEscala;
    private String descricaoEscala;

    public Escala (int codigoEscala, String descricaoEscala){
        this.codigoEscala = codigoEscala;
        this.descricaoEscala = descricaoEscala;
    }

    public void exibirDetalhesEscala() {
        
    }

    public int getCodigo() {
        return this.codigoEscala;
    }

    public void setCodigoEscala(int codigoEscala) {
        this.codigoEscala = codigoEscala;
    }

    public String getDescricao() {
        return this.descricaoEscala;
    }

    public void setDescricaoEscala(String descricaoEscala) {
        this.descricaoEscala = descricaoEscala;
    }
}
