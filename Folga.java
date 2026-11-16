public class Folga {
    private String dataFolga;
    private String tipoFolga;
    private boolean statusFolga;

    public Folga (String dataFolga, String tipoFolga){
        this.dataFolga = dataFolga;
        this.tipoFolga = tipoFolga;
        this.statusFolga = false;
    }

    public void registroFolga() {

    }

    public String getDataFolga() {
        return dataFolga;
    }

    public void setDataFolga(String dataFolga) {
        this.dataFolga = dataFolga;
    }

    public String getTipoFolga() {
        return tipoFolga;
    }

    public void setTipoFolga(String tipoFolga) {
        this.tipoFolga = tipoFolga;
    }

    public boolean isStatusFolga() {
        return statusFolga;
    }

    public void setStatusFolga(boolean statusFolga) {
        this.statusFolga = statusFolga;
    }
}