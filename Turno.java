public class Turno {
    private String horarioInicio;
    private String horarioFim;
    private String descricaoTurno;

    public Turno(String horarioInicio, String horarioFim, String descricaoTurno) {
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.descricaoTurno = descricaoTurno;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(String horarioFim) {
        this.horarioFim = horarioFim;
    }

    public String getDescricaoTurno() {
        return descricaoTurno;
    }

    public void setDescricaoTurno(String descricaoTurno) {
        this.descricaoTurno = descricaoTurno;
    }

    public boolean validarHorarioTurno() { 
        return true; 
    }
}
