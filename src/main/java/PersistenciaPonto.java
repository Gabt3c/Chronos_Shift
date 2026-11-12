import java.io.IOException;
import java.util.List;

public interface PersistenciaPonto {
    // Acrescenta uma nova marcação, preserva as anteriores e devolve seu identificador.
    long salvarRegistro(RegistroPonto registro) throws IOException;

    // Recupera o histórico completo para consultas e novas inserções.
    List<RegistroPonto> carregarRegistros() throws IOException;
}
