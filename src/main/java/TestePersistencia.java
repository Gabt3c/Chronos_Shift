import java.nio.file.Path;

public class TestePersistencia {
    public static void main(String[] args) {
        // Usa um arquivo separado para a apresentação e para execuções repetidas.
        PersistenciaPonto persistencia = new PersistenciaPontoJson(
            Path.of("data", "demonstracao-eventos.json"));
        Colaborador colaborador = new Colaborador("Ana", "00000000000", 1, persistencia);

        // O modo consultar apenas recupera o histórico, sem modificar o arquivo.
        if (args.length == 0 || !args[0].equals("consultar")) {
            colaborador.registrarEntrada();
            colaborador.registrarInicioPausa();
            colaborador.registrarFimPausa();
            colaborador.registrarSaida();
        }

        // Em outra execução, os registros do mesmo arquivo continuam disponíveis.
        for (RegistroPonto registro : colaborador.consultarHistoricoPontos()) {
            System.out.println("Registro: " + registro.getId());
            System.out.println("Colaborador: " + registro.getCodigoColaborador());
            System.out.println("Tipo: " + registro.getTipo());
            System.out.println("Data e hora do evento: " + registro.getDataHora());
            System.out.println("Data: " + registro.getDataReg());
            System.out.println("Entrada: " + registro.getHorarioEntrada());
            System.out.println("Saída: " + registro.getHorarioSaida());
        }
    }
}
