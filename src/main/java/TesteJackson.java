import java.nio.file.Path;
import java.util.List;

public class TesteJackson {
    public static void main(String[] args) throws Exception {
        // O código identifica a pessoa e não é calculado pelo tamanho do histórico.
        int codigoColaborador = args.length > 0 ? Integer.parseInt(args[0]) : 1;
        String tipo = args.length > 1 ? args[1] : "ENTRADA";
        Path arquivo = args.length > 2 ? Path.of(args[2])
            : Path.of("data", "registros-ponto.json");

        // Usa a mesma integração do projeto, evitando duas formas diferentes de salvar.
        PersistenciaPonto persistencia = new PersistenciaPontoJson(arquivo);
        Colaborador colaborador = new Colaborador("Colaborador de teste", "00000000000",
            codigoColaborador, persistencia);

        // Cada ação gera um novo ID de registro para o mesmo código do colaborador.
        switch (tipo) {
            case "ENTRADA":
                colaborador.registrarEntrada();
                break;
            case "SAIDA":
                colaborador.registrarSaida();
                break;
            case "INICIO_PAUSA":
                colaborador.registrarInicioPausa();
                break;
            case "FIM_PAUSA":
                colaborador.registrarFimPausa();
                break;
            case "CONSULTAR":
                break;
            default:
                throw new IllegalArgumentException("Tipo de marcação inválido.");
        }

        // Recupera o arquivo e mostra os dois identificadores de cada marcação.
        List<RegistroPonto> registros = colaborador.consultarHistoricoPontos();
        for (RegistroPonto registro : registros) {
            System.out.println("ID do registro: " + registro.getId()
                + " | Código do colaborador: " + registro.getCodigoColaborador()
                + " | Tipo: " + registro.getTipo()
                + " | Data e hora: " + registro.getDataHora());
        }
    }
}