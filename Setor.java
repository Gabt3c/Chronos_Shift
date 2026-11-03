public class Setor {
    // Atributos do setor e colaborador vinculado a ele.
    private int codigoSetor;
    private String nomeSetor;
    private Colaborador colaborador;

    // Construtor para criar um setor com um colaborador inserido.
    public Setor(int codigoSetor, String nomeSetor, Colaborador colaborador) {
        this.codigoSetor = codigoSetor;
        this.nomeSetor = nomeSetor;
        setColaborador(colaborador);
    }

    // Métodos de leitura dos dados do setor.
    public int getCodigoSetor() {
        return codigoSetor;
    }

    public String getNomeSetor() {
        return nomeSetor;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    // Métodos para atualizar os dados do setor.
    public void setCodigoSetor(int codigoSetor) {
        this.codigoSetor = codigoSetor;
    }

    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    // Impede que o setor fique sem um colaborador vinculado.
    public void setColaborador(Colaborador colaborador) {
        if (colaborador == null) {
            throw new IllegalArgumentException("O setor deve possuir um colaborador.");
        }
        this.colaborador = colaborador;
    }
}
