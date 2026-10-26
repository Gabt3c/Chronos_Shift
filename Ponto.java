import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Ponto {
    private String dataReg;
    private String horarioEntrada;
    private String horarioSaida;
    private String pausaInicio;
    private String pausaFim;

    public void setDataReg(String dataReg) {
        this.dataReg = dataReg;
    }
    
    public String getDataReg() {
        return this.dataReg;
    }



    public void registrarEntrada() {
        LocalTime agora = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.horarioEntrada = agora.format(formatter);

    }

    public void registrarSaida() {
        LocalTime agora = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.horarioSaida = agora.format(formatter);


    }

    public void registrarInicioPausa() {
        LocalTime agora = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.pausaInicio = agora.format(formatter);

    }

    public void registrarFimPausa() {
        LocalTime agora = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.pausaFim = agora.format(formatter);
    }

    public void ajustarPonto(String novaEntrada, String novaSaida, String novaPausaInicio, String novaPausaFim) {
        if (novaEntrada != null && !novaEntrada.isEmpty()) {
            this.horarioEntrada = novaEntrada;
        }
        if (novaSaida != null && !novaSaida.isEmpty()) {
            this.horarioSaida = novaSaida;
        }
        if (novaPausaInicio != null && !novaPausaInicio.isEmpty()) {
            this.pausaInicio = novaPausaInicio;
        }
        if (novaPausaFim != null && !novaPausaFim.isEmpty()) {
            this.pausaFim = novaPausaFim;
        }

    }



}

