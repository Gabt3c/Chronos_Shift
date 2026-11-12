public class TesteOrganizacao {
    public static void main(String[] args) {
        // Cria colaboradores usando o construtor que já existe no projeto.
        Colaborador c1 = new Colaborador("Ana", "00000000000", 1);
        Colaborador c2 = new Colaborador("Bruno", "11111111111", 2);

        // Cria diferentes setores, cada um com seu colaborador.
        Setor s1 = new Setor(1, "Atendimento", c1);
        Setor s2 = new Setor(2, "Suporte", c2);

        // Cria diferentes escalas para os mesmos colaboradores dos setores.
        Escala e1 = new Escala(1, "Segunda a sexta, das 08:00 às 17:00", c1);
        Escala e2 = new Escala(2, "Segunda a sexta, das 09:00 às 18:00", c2);

        // Usa os setters para atualizar os dados antes da consulta.
        s2.setCodigoSetor(3);
        s2.setNomeSetor("Suporte técnico");
        s2.setColaborador(c1);
        s2.setColaborador(c2);
        e2.setCodigoEscala(3);
        e2.setDescricaoEscala("Segunda a sexta, das 10:00 às 19:00");
        e2.setColaborador(c1);
        e2.setColaborador(c2);

        // Exibe os setores e seus colaboradores por meio dos getters.
        System.out.println("Setor " + s1.getCodigoSetor() + ": " + s1.getNomeSetor());
        System.out.println("Colaborador: " + s1.getColaborador().getNome());
        System.out.println("Setor " + s2.getCodigoSetor() + ": " + s2.getNomeSetor());
        System.out.println("Colaborador: " + s2.getColaborador().getNome());

        // Exibe as escalas pelo método definido no UML.
        e1.exibirDetalhesEscala();
        e2.exibirDetalhesEscala();

        // Confere se as alterações e os vínculos foram mantidos corretamente.
        if (s2.getCodigoSetor() != 3 || !s2.getNomeSetor().equals("Suporte técnico")
                || s1.getColaborador() != c1 || s2.getColaborador() != c2
                || e1.getColaborador() != c1 || e2.getColaborador() != c2
                || e2.getCodigoEscala() != 3
                || !e2.getDescricaoEscala().equals("Segunda a sexta, das 10:00 às 19:00")) {
            throw new IllegalStateException("Os dados da organização estão incorretos.");
        }

        // Confere se as classes impedem a criação de vínculos vazios.
        try {
            new Setor(4, "Sem colaborador", null);
            throw new IllegalStateException("O setor aceitou um colaborador nulo.");
        } catch (IllegalArgumentException erro) {
            System.out.println(erro.getMessage());
        }
        try {
            e2.setColaborador(null);
            throw new IllegalStateException("A escala aceitou um colaborador nulo.");
        } catch (IllegalArgumentException erro) {
            System.out.println(erro.getMessage());
        }

        // Plantao será incluído após a atualização do UML solicitada pelo grupo.
        System.out.println("Teste de setores e escalas concluído.");
    }
}
