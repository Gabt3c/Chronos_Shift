public class BancoHoras {
    private double saldoHoras;
    private double horasExtras;
    private double horasFaltantes;

    public BancoHoras() {
        this.saldoHoras = 0.0;
        this.horasExtras = 0.0;
        this.horasFaltantes = 0.0;
    }

    public BancoHoras(double saldoHoras, double horasExtras, double horasFaltantes) {
        this.saldoHoras = saldoHoras;
        this.horasExtras = horasExtras;
        this.horasFaltantes = horasFaltantes;
    }

    public double consultarSaldo() { 
        return 0.0;
     }

    public double calcularHorasExtras() { 
        return 0.0;
     }

    public void calcularHorasFaltantes() {
        
    }

    public void adicionarHoras(double qtdHoras) {

    }
    
    public void compensarHoras(double qtdHoras) {

    }

    public double getSaldoHoras() {
        return saldoHoras;
    }

    public void setSaldoHoras(double saldoHoras) {
        this.saldoHoras = saldoHoras;
    }

    public double getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(double horasExtras) {
        this.horasExtras = horasExtras;
    }

    public double getHorasFaltantes() {
        return horasFaltantes;
    }

    public void setHorasFaltantes(double horasFaltantes) {
        this.horasFaltantes = horasFaltantes;
    }
}
