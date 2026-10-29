import java.util.List;
import java.util.ArrayList;

public class Colaborador {
    // Atributos definidos no diagrama UML.
    private String nome;
    private String cpfColaborador;
    private int codigoColaborador;
    private List<Ponto> pontos;

    // Construtor para identificar o colaborador.
    public Colaborador(String nome, String cpfColaborador, int codigoColaborador) {
        this.nome = nome;
        this.cpfColaborador = cpfColaborador;
        this.codigoColaborador = codigoColaborador;
        this.pontos = new ArrayList<>();
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
