public class TesteColaborador {
    public static void main(String[] args) {
        // Cria um colaborador e um registro de ponto para os testes.
        Colaborador colaborador = new Colaborador("Colaborador Teste", "00000000000", 1);
        Ponto ponto = new Ponto();

        // Testa o registro de entrada pelo colaborador.
        colaborador.registrarPonto(ponto);
        verificar(ponto.getHorarioEntrada() != null, "Registro de entrada");

        // Testa a proteção contra entrada duplicada.
        String primeiraEntrada = ponto.getHorarioEntrada();
        colaborador.registrarPonto(ponto);
        verificar(primeiraEntrada.equals(ponto.getHorarioEntrada()), "Entrada duplicada");

        // Testa uma solicitação sem justificativa.
        colaborador.solicitarAjustePonto(ponto);
        verificar(colaborador.getPontoAguardandoAjuste() == null, "Solicitação sem justificativa");

        // Registra uma justificativa e solicita o ajuste sem modificar os horários.
        ponto.ajustarPonto("Esqueci de registrar a saída.");
        colaborador.solicitarAjustePonto(ponto);
        verificar(colaborador.getPontoAguardandoAjuste() == ponto, "Solicitação com justificativa");

        // Testa os métodos de pausa e saída que já existem na classe Ponto.
        ponto.registrarInicioPausa();
        ponto.registrarFimPausa();
        ponto.registrarSaida();
        verificar(ponto.getPausaInicio() != null && ponto.getPausaFim() != null
                && ponto.getHorarioSaida() != null, "Pausa e saída");
    }

    // Exibe o resultado de cada verificação, sem depender da Main do projeto.
    private static void verificar(boolean condicao, String descricao) {
        if (condicao) {
            System.out.println("PASSOU: " + descricao);
        } else {
            throw new AssertionError("FALHOU: " + descricao);
        }
    }
}
