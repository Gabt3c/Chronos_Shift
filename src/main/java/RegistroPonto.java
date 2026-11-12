public class RegistroPonto {
    // Dados simples que serão gravados no JSON, sem textos de apresentação.
    private long id;
    private int codigoColaborador;
    private String tipo;
    private java.time.LocalDateTime dataHora;
    private String dataReg;
    private String horarioEntrada;
    private String horarioSaida;
    private String pausaInicio;
    private String pausaFim;
    private String justificativa;

    // Construtor vazio usado pelo Jackson ao recuperar os registros do arquivo.
    public RegistroPonto() {
    }

    // Métodos de leitura dos dados do registro.
    public long getId() {
        return id;
    }
    public int getCodigoColaborador() {
        return codigoColaborador;
    }
    public String getTipo() {
        return tipo;
    }
    public java.time.LocalDateTime getDataHora() {
        return dataHora;
    }
    public String getDataReg() {
        return dataReg;
    }
    public String getHorarioEntrada() {
        return horarioEntrada;
    }
    public String getHorarioSaida() {
        return horarioSaida;
    }
    public String getPausaInicio() {
        return pausaInicio;
    }
    public String getPausaFim() {
        return pausaFim;
    }
    public String getJustificativa() {
        return justificativa;
    }

    // Métodos usados para preencher e atualizar os dados do registro.
    public void setId(long id) {
        this.id = id;
    }
    public void setCodigoColaborador(int codigoColaborador) {
        this.codigoColaborador = codigoColaborador;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setDataHora(java.time.LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    public void setDataReg(String dataReg) {
        this.dataReg = dataReg;
    }
    public void setHorarioEntrada(String horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }
    public void setHorarioSaida(String horarioSaida) {
        this.horarioSaida = horarioSaida;
    }
    public void setPausaInicio(String pausaInicio) {
        this.pausaInicio = pausaInicio;
    }
    public void setPausaFim(String pausaFim) {
        this.pausaFim = pausaFim;
    }
    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }
}
