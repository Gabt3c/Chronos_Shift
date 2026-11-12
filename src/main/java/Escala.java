
public class Escala {
    // Atributos da escala e colaborador vinculado a ela.
    private int codigoEscala;
    private String descricaoEscala;
    private Colaborador colaborador;

    // Construtor para criar uma escala com um colaborador inserido.
    public Escala(int codigoEscala, String descricaoEscala, Colaborador colaborador) {
        this.codigoEscala = codigoEscala;
        this.descricaoEscala = descricaoEscala;
        setColaborador(colaborador);
    }

    // Exibe os dados da escala e identifica seu colaborador.
    public void exibirDetalhesEscala() {
        System.out.println("Código da escala: " + this.codigoEscala);
        System.out.println("Descrição da escala: " + this.descricaoEscala);
        System.out.println("Colaborador: " + this.colaborador.getNome());
        System.out.println("Código do colaborador: " + this.colaborador.getCodigoColaborador());
    }

    // Métodos de leitura dos dados da escala.
    public int getCodigoEscala() {
        return codigoEscala;
    }

    public String getDescricaoEscala() {
        return descricaoEscala;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    // Métodos para atualizar os dados da escala.
    public void setCodigoEscala(int codigoEscala) {
        this.codigoEscala = codigoEscala;
    }

    public void setDescricaoEscala(String descricaoEscala) {
        this.descricaoEscala = descricaoEscala;
    }

    // Impede que a escala fique sem um colaborador vinculado.
    public void setColaborador(Colaborador colaborador) {
        if (colaborador == null) {
            throw new IllegalArgumentException("A escala deve possuir um colaborador.");
        }
        this.colaborador = colaborador;
    }
}
