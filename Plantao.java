public class Plantao {
    private int codigoPlantao;
    private String dataPlantao;

    public Plantao(int codigoPlantao, String dataPlantao){
        this.codigoPlantao = codigoPlantao;
        this.dataPlantao = dataPlantao;
    }

    public void registrarTrocaPlantao() {
        
    }

    public int getCodigoPlantao() {
        return codigoPlantao;
    }

    public void setCodigoPlantao(int codigoPlantao) {
        this.codigoPlantao = codigoPlantao;
    }

    public String getDataPlantao() {
        return dataPlantao;
    }

    public void setDataPlantao(String dataPlantao) {
        this.dataPlantao = dataPlantao;
    }
}
