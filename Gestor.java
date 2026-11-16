public class Gestor extends Colaborador {

    public Gestor(String nome, String cpfGestor, int codigoGestor){
        super(nome, cpfGestor, codigoGestor);
    }
    public void criarEscala(Escala escala) {

    }

    public void editarPlantao(Plantao plantao) {
        
    }

    public boolean validarDescansoMinimo() { 
        return true; 
    }

    public boolean aprovarFolga(Folga folga) { 
        return true; 
    }

    public boolean aprovarAjustePonto(Ponto ponto) { 
        return true;
     }

    public boolean aprovarTrocaPlantao(Plantao plantao) {
         return true;
     }
}
