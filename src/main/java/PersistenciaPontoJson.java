import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersistenciaPontoJson implements PersistenciaPonto {
    // Cada instância guarda o caminho do arquivo e a configuração do Jackson.
    private final Path arquivo;
    private final ObjectMapper mapper;

    // O caminho pode ser trocado nos testes sem modificar o arquivo de apresentação.
    public PersistenciaPontoJson(Path arquivo) {
        if (arquivo == null) {
            throw new IllegalArgumentException("Informe o caminho do arquivo de pontos.");
        }
        this.arquivo = arquivo.toAbsolutePath().normalize();
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.mapper.enable(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
    }

    // Arquivo ausente ou somente com espaços representa um histórico ainda vazio.
    @Override
    public List<RegistroPonto> carregarRegistros() throws IOException {
        if (!Files.exists(this.arquivo)) {
            return new ArrayList<>();
        }
        if (Files.readString(this.arquivo).isBlank()) {
            return new ArrayList<>();
        }

        // Mantém a leitura de listas usada pelo Arthur e rejeita conteúdo incompatível.
        List<RegistroPonto> registros = this.mapper.readValue(
            this.arquivo.toFile(), new TypeReference<ArrayList<RegistroPonto>>() {}
        );
        if (registros == null) {
            throw new IOException("O arquivo deve conter uma lista de registros, não null.");
        }
        Set<Long> ids = new HashSet<>();
        for (RegistroPonto registro : registros) {
            validarRegistro(registro);
            if (registro.getId() <= 0 || !ids.add(registro.getId())) {
                throw new IOException("O histórico contém um ID inválido ou repetido.");
            }
        }
        return registros;
    }

    // Lê antes de salvar para conservar os pontos dos outros dias e colaboradores.
    @Override
    public long salvarRegistro(RegistroPonto registro) throws IOException {
        validarRegistro(registro);
        List<RegistroPonto> registros = carregarRegistros();
        long id = registro.getId();
        if (id != 0) {
            throw new IOException("Uma nova marcação deve chegar sem ID. O histórico não é sobrescrito.");
        }

        // Um registro novo usa o maior ID existente, sem depender do tamanho da lista.
        if (id == 0) {
            long maiorId = 0;
            for (RegistroPonto existente : registros) {
                maiorId = Math.max(maiorId, existente.getId());
            }
            if (maiorId == Long.MAX_VALUE) {
                throw new IOException("Não há identificadores disponíveis.");
            }
            id = maiorId + 1;
            registro.setId(id);
            registros.add(registro);
        }

        // Escreve primeiro em um arquivo temporário, preservando o anterior em caso de erro.
        Files.createDirectories(this.arquivo.getParent());
        Path temporario = Files.createTempFile(this.arquivo.getParent(), "pontos-", ".tmp");
        try {
            this.mapper.writeValue(temporario.toFile(), registros);
            try {
                Files.move(temporario, this.arquivo,
                    StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException erro) {
                Files.move(temporario, this.arquivo, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temporario);
        }
        return id;
    }

    // Impede que dados sem identificação ou data válida entrem no histórico.
    private void validarRegistro(RegistroPonto registro) throws IOException {
        if (registro == null || registro.getCodigoColaborador() < 0
                || registro.getDataHora() == null || registro.getTipo() == null
                || !List.of("ENTRADA", "SAIDA", "INICIO_PAUSA", "FIM_PAUSA", "AJUSTE").contains(registro.getTipo())
                || registro.getDataReg() == null || registro.getDataReg().isBlank()) {
            throw new IOException("Registro sem colaborador ou data válida.");
        }
        try {
            LocalDate.parse(registro.getDataReg(), DateTimeFormatter.ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT));
        } catch (java.time.format.DateTimeParseException erro) {
            throw new IOException("Data inválida no registro de ponto.", erro);
        }
    }
}
