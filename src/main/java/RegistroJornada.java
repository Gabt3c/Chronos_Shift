public interface RegistroJornada {
    // Operações de registro de ponto que uma implementação deve oferecer.
    void registrarEntrada();
    void registrarSaida();
    void registrarInicioPausa();
    void registrarFimPausa();
    void ajustarPonto(String novaEntrada, String novaSaida, String novaPausaInicio,
        String novaPausaFim, String justificativa);
}
