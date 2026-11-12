import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PersistenciaPontoTest {
    // Cada teste recebe uma pasta temporária, sem alterar os dados do projeto.
    @TempDir
    Path pasta;

    private RegistroPonto criarRegistro(int colaborador, String data) {
        RegistroPonto registro = new RegistroPonto();
        registro.setCodigoColaborador(colaborador);
        registro.setDataReg(data);
        registro.setTipo("ENTRADA");
        registro.setDataHora(java.time.LocalDateTime.of(2026, 11, 12, 8, 0));
        registro.setHorarioEntrada("08:00:00");
        return registro;
    }

    // Confere primeira execução, recuperação por outra instância e novas inserções.
    @Test
    void salvaERecuperaSemApagarAnteriores() throws Exception {
        Path arquivo = pasta.resolve("dados/pontos.json");
        PersistenciaPontoJson json = new PersistenciaPontoJson(arquivo);
        assertTrue(json.carregarRegistros().isEmpty());
        assertEquals(1, json.salvarRegistro(criarRegistro(1, "10/11/2026")));
        PersistenciaPontoJson reaberto = new PersistenciaPontoJson(arquivo);
        assertEquals(1, reaberto.carregarRegistros().size());
        assertEquals(2, reaberto.salvarRegistro(criarRegistro(2, "11/11/2026")));
        List<RegistroPonto> registros = json.carregarRegistros();
        assertEquals(2, registros.size());
        assertEquals("10/11/2026", registros.get(0).getDataReg());
    }

    // Cada marcação recebe outro ID, sem modificar o evento anterior.
    @Test
    void criaOutroEventoSemAlterarAnterior() throws Exception {
        PersistenciaPontoJson json = new PersistenciaPontoJson(pasta.resolve("pontos.json"));
        RegistroPonto registro = criarRegistro(1, "10/11/2026");
        json.salvarRegistro(registro);
        RegistroPonto saida = criarRegistro(1, "10/11/2026");
        saida.setTipo("SAIDA");
        saida.setHorarioSaida("17:00:00");
        assertEquals(2, json.salvarRegistro(saida));
        assertEquals(2, json.carregarRegistros().size());
        assertEquals("ENTRADA", json.carregarRegistros().get(0).getTipo());
        assertNull(json.carregarRegistros().get(0).getHorarioSaida());
        assertEquals("SAIDA", json.carregarRegistros().get(1).getTipo());
        assertEquals("17:00:00", json.carregarRegistros().get(1).getHorarioSaida());
    }

    // A sequência usa o maior ID mesmo quando a lista contém lacunas.
    @Test
    void geraIdPeloMaiorExistente() throws Exception {
        Path arquivo = pasta.resolve("pontos.json");
        Files.writeString(arquivo,
            "[{\"id\":9,\"tipo\":\"ENTRADA\",\"dataHora\":\"2026-11-12T08:00:00\",\"codigoColaborador\":1,\"dataReg\":\"10/11/2026\"}]");
        PersistenciaPontoJson json = new PersistenciaPontoJson(arquivo);
        assertEquals(10, json.salvarRegistro(criarRegistro(2, "11/11/2026")));
    }

    // Um arquivo vazio pode receber seu primeiro registro.
    @Test
    void aceitaArquivoVazio() throws Exception {
        Path arquivo = pasta.resolve("pontos.json");
        Files.writeString(arquivo, "  \n");
        PersistenciaPontoJson json = new PersistenciaPontoJson(arquivo);
        assertTrue(json.carregarRegistros().isEmpty());
        json.salvarRegistro(criarRegistro(1, "10/11/2026"));
        assertEquals(1, json.carregarRegistros().size());
    }

    // Conteúdos incompatíveis geram erro e permanecem intactos para análise.
    @Test
    void preservaArquivoInvalido() throws Exception {
        Path arquivo = pasta.resolve("pontos.json");
        PersistenciaPontoJson json = new PersistenciaPontoJson(arquivo);
        String[] invalidos = {"{quebrado", "null", "[null]", "{}",
            "[] lixo", "[{\"id\":1,\"tipo\":\"ENTRADA\"}]",
            "[{\"id\":1,\"codigoColaborador\":1,\"dataReg\":\"31/02/2026\"}]",
            "[{\"id\":1,\"codigoColaborador\":1,\"dataReg\":\"10/11/2026\"},"
            + "{\"id\":1,\"codigoColaborador\":2,\"dataReg\":\"10/11/2026\"}]"};
        for (String conteudo : invalidos) {
            Files.writeString(arquivo, conteudo);
            assertThrows(IOException.class, () -> json.salvarRegistro(criarRegistro(1, "10/11/2026")));
            assertEquals(conteudo, Files.readString(arquivo));
        }
    }

    // Uma atualização não pode usar o ID de outro colaborador nem inventar um ID.
    @Test
    void rejeitaAtualizacaoIncompativel() throws Exception {
        Path arquivo = pasta.resolve("pontos.json");
        PersistenciaPontoJson json = new PersistenciaPontoJson(arquivo);
        json.salvarRegistro(criarRegistro(1, "10/11/2026"));
        String antes = Files.readString(arquivo);
        RegistroPonto outro = criarRegistro(2, "10/11/2026");
        outro.setId(1);
        assertThrows(IOException.class, () -> json.salvarRegistro(outro));
        outro.setId(99);
        assertThrows(IOException.class, () -> json.salvarRegistro(outro));
        assertEquals(antes, Files.readString(arquivo));
    }

    // Simula reabertura, pausa e saída usando as classes reais do projeto.
    @Test
    void integraColaboradorPausaESaidaAposReabrir() throws Exception {
        Path arquivo = pasta.resolve("pontos.json");
        Colaborador ana = new Colaborador("Ana", "000", 1, new PersistenciaPontoJson(arquivo));
        ana.registrarEntrada();
        Colaborador reaberta = new Colaborador("Ana", "000", 1, new PersistenciaPontoJson(arquivo));
        assertEquals(ana.getHorarioEntrada(), reaberta.getHorarioEntrada());
        reaberta.registrarPausa();
        assertNotNull(reaberta.consultarHistoricoPontos().get(1).getPausaInicio());
        assertNull(reaberta.consultarHistoricoPontos().get(1).getPausaFim());
        reaberta.registrarPausa();
        reaberta.registrarSaida();
        RegistroPonto registro = reaberta.consultarHistoricoPontos().get(3);
        assertNotNull(registro.getPausaFim());
        assertNotNull(registro.getHorarioSaida());
        assertFalse(registro.getHorarioEntrada().contains("Horário"));
        assertEquals(4, reaberta.consultarHistoricoPontos().size());
        List<RegistroPonto> eventos = reaberta.consultarHistoricoPontos();
        assertEquals(List.of("ENTRADA", "INICIO_PAUSA", "FIM_PAUSA", "SAIDA"),
            eventos.stream().map(RegistroPonto::getTipo).toList());
        assertEquals(List.of(1L, 2L, 3L, 4L), eventos.stream().map(RegistroPonto::getId).toList());
        assertTrue(eventos.stream().allMatch(evento -> evento.getCodigoColaborador() == 1));
        Colaborador bruno = new Colaborador("Bruno", "111", 2, new PersistenciaPontoJson(arquivo));
        bruno.registrarEntrada();
        assertEquals(1, bruno.consultarHistoricoPontos().size());
        assertEquals(5, new PersistenciaPontoJson(arquivo).carregarRegistros().size());
    }

    // Confere ajustes e separação de datas no histórico do mesmo colaborador.
    @Test
    void preservaDiasAnterioresEAjustes() throws Exception {
        Path arquivo = pasta.resolve("pontos.json");
        Ponto ponto = new Ponto(1, new PersistenciaPontoJson(arquivo));
        ponto.setDataReg("10/11/2020");
        ponto.ajustarPonto("08:00:00", "17:00:00", "12:00:00", "13:00:00", "Correção");
        ponto.registrarEntrada();
        List<RegistroPonto> historico = ponto.consultarHistoricoPontos();
        assertEquals(3, historico.size());
        assertEquals("Correção", historico.get(1).getJustificativa());
        assertEquals("AJUSTE", historico.get(1).getTipo());
        assertNull(historico.get(2).getHorarioSaida());
        assertNull(historico.get(2).getJustificativa());
    }

    // Erros do arquivo são comunicados pelo ponto em vez de serem ignorados.
    @Test
    void informaFalhaDeLeituraNoColaborador() throws Exception {
        Path arquivo = pasta.resolve("pontos.json");
        Files.writeString(arquivo, "quebrado");
        assertThrows(UncheckedIOException.class, () ->
            new Colaborador("Ana", "000", 1, new PersistenciaPontoJson(arquivo)));
        assertEquals("quebrado", Files.readString(arquivo));
    }
}
