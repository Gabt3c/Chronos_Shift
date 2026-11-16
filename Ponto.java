import java.util.ArrayList;
import java.util.List;

public class Ponto {
    private String dataReg;
    private String horarioEntrada;
    private String horarioSaida;
    private String pausaInicio;
    private String pausaFim;
    private String justificativa;
    private List<Ponto> historicoPontos;

    public Ponto() {
        this.historicoPontos = new ArrayList<>();
    }

    public Ponto(String dataReg, String horarioEntrada, String horarioSaida) {
        this.dataReg = dataReg;
        this.horarioEntrada = horarioEntrada;
        this.horarioSaida = horarioSaida;
        this.historicoPontos = new ArrayList<>();
    }
    
    public void salvarPonto() {

    }

    public void registrarEntrada() {

    }

    public void registrarSaida() {

    }

    public void registrarInicioPausa() {

    }

    public void registrarFimPausa() {

    }

    public void justificarSugestao(String justificativa) {

    }

    public void ajustarPonto(String novaEntrada, String novaSaida, String novaPausaInicio, String novaPausaFim, String justificativa) {

    }

    public String getDataReg() {
        return dataReg;
    }

    public void setDataReg(String dataReg) {
        this.dataReg = dataReg;
    }

    public String getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(String horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public String getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(String horarioSaida) {
        this.horarioSaida = horarioSaida;
    }

    public String getPausaInicio() {
        return pausaInicio;
    }

    public void setPausaInicio(String pausaInicio) {
        this.pausaInicio = pausaInicio;
    }

    public String getPausaFim() {
        return pausaFim;
    }

    public void setPausaFim(String pausaFim) {
        this.pausaFim = pausaFim;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public List<Ponto> getHistoricoPontos() {
        return historicoPontos;
    }

    public void setHistoricoPontos(List<Ponto> historicoPontos) {
        this.historicoPontos = historicoPontos;
    }
}
