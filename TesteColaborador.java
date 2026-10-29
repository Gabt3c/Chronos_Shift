public class TesteColaborador {
    public static void main(String[] args) {
     Colaborador c1 = new Colaborador("col1", "000000", 2);
     c1.registrarEntrada();
     System.out.println(c1.getHorarioEntrada());
    }
}
