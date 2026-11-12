# Persistência de marcações com Jackson

## Ajuste solicitado pelo Gabriel

Cada marcação tem seu próprio id. O codigoColaborador identifica a pessoa e permanece igual nas marcações dela. Entrada, início de pausa, fim de pausa e saída geram quatro registros independentes. Uma nova execução não reinicia a numeração, pois o próximo ID é o maior ID salvo mais um.

Exemplo resumido dos campos principais:

```json
[
  {"id": 1, "codigoColaborador": 10, "tipo": "ENTRADA", "dataHora": "2026-11-12T08:00:00"},
  {"id": 2, "codigoColaborador": 10, "tipo": "SAIDA", "dataHora": "2026-11-12T17:00:00"},
  {"id": 3, "codigoColaborador": 20, "tipo": "ENTRADA", "dataHora": "2026-11-12T09:00:00"}
]
```

O JSON completo também guarda dataReg, os horários acumulados e a justificativa como estavam naquele momento. Esses campos permitem reconstruir os getters das classes existentes ao reabrir o programa. Eles não transformam o evento em registro diário: cada ação continua acrescentando uma linha na lista com um novo ID.

## Arquivos e responsabilidade de cada bloco

- RegistroJornada.java: contrato com as operações de entrada, saída, pausa e ajuste.
- PersistenciaPonto.java: contrato para inserir eventos e recuperar o histórico.
- RegistroPonto.java: dados de cada evento, com construtor vazio, getters e setters explícitos para o Jackson.
- PersistenciaPontoJson.java: configura Jackson e JavaTimeModule; lê a lista; valida o conteúdo; calcula o próximo ID; acrescenta o evento; escreve em temporário e substitui o destino. Não atualiza eventos antigos por ID. Eventos novos devem chegar com ID 0, e a persistência atribui o ID definitivo.
- Ponto.java: implementa as operações, mantém os getters originais e salva o tipo de cada marcação. Ao reabrir, recupera os valores do evento de maior ID de hoje daquele colaborador. Ajustes são novos eventos AJUSTE, preservando os anteriores. O ajuste completo gera apenas um evento com todos os horários e a justificativa.
- Colaborador.java: passa seu código fixo ao Ponto e permite consultar somente seus registros. O construtor antigo continua funcionando. registrarPausa delega à alternância de início e fim já existente em Ponto.
- TesteJackson.java: recebe código do colaborador e tipo de marcação, chama as classes reais e mostra os dois IDs separados. O modo CONSULTAR não grava nada.
- TestePersistencia.java: demonstra uma sequência de quatro marcações e a recuperação em outra execução.
- PersistenciaPontoTest.java: nove testes automatizados em pastas temporárias.

Os blocos novos têm comentários em português. A entrega conserva a estrutura Maven do Gabriel e a ideia do Arthur de ler a lista, acrescentar e salvar. O código original do TesteJackson do Arthur foi guardado em referencias/TesteJackson-Arthur.java.txt; data/teste-registro.json também foi preservado. A nova implementação utiliza outro arquivo.

## Executar e testar

Use Maven e JDK 25 ou superior, com JAVA_HOME configurado. Foram mantidos release 25 e Jackson 2.22.4 do modelo. Os testes foram executados com JDK 26.0.2 e Maven 3.9.9.

```powershell
mvn test
mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass=TesteJackson "-Dexec.args=10 ENTRADA"
mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass=TesteJackson "-Dexec.args=10 SAIDA"
mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass=TesteJackson "-Dexec.args=10 CONSULTAR"
```

O arquivo data/registros-ponto.json será criado. As duas primeiras chamadas devem gerar IDs diferentes com codigoColaborador 10. A consulta lista os dois eventos, sem alterar o arquivo. Um terceiro argumento opcional de TesteJackson permite informar outro caminho de arquivo. Execute os comandos na raiz que contém pom.xml, pois os caminhos padrão são relativos à pasta atual.

TestePersistencia utiliza data/demonstracao-eventos.json. A primeira execução sem argumentos gera quatro eventos; outra execução com consultar apenas recupera o histórico. Repetir uma marcação também gera outro evento, mesmo no mesmo dia.

## Preservação dos arquivos anteriores

A versão diária anterior utilizava data/pontos.json. Esse arquivo não é apagado nem reinterpretado automaticamente: o formato de eventos utiliza data/registros-ponto.json. O arquivo antigo do Arthur usa id/tipo/dataHora sem identificar colaborador, então não há informação suficiente para atribuir automaticamente esses eventos a pessoas. Ele continua em data/teste-registro.json para consulta.

Dados gerados localmente e target não vão para o Git. Os códigos dos colaboradores devem ser únicos e estáveis. Nenhum método desta entrega incrementa o código do colaborador.

## Verificações

Os testes cobrem inserção sem perda do histórico; recuperação por outra instância; eventos distintos para entrada e saída; IDs com lacunas; arquivo vazio; rejeição de arquivos inválidos sem apagar o conteúdo; rejeição de tentativa de sobrescrever um ID; sequência dos quatro tipos de marcação com o mesmo colaborador; separação entre colaboradores; ajustes como eventos; e erros de leitura comunicados ao chamador.

Arquivos inválidos não são tratados silenciosamente como históricos vazios. Ao salvar, o programa primeiro lê e valida o histórico, evitando substituir dados que não conseguiu interpretar.

## Limites

O uso previsto é local e com um programa gravando por vez. Não há controle de concorrência entre processos. A troca do arquivo tenta operação atômica e utiliza substituição normal caso o sistema não suporte. Não substitui backups.

Não foram adicionadas regras de ordem de horários, cálculo de jornada ou múltiplos intervalos aos getters antigos. Cada marcação repetida fica no histórico, mas os getters mostram o estado mais recente. Jornadas atravessando a meia-noite ainda exigem uma regra do grupo. O getter estático gethistoricoPontos continua representando somente objetos em memória; consulte consultarHistoricoPontos para recuperar os eventos do arquivo.

No construtor Ponto() sem colaborador, código 0 é mantido por compatibilidade; no fluxo real use Colaborador com seu código. Erros de persistência são propagados como UncheckedIOException em Ponto/Colaborador. Os campos em memória podem refletir uma tentativa que falhou, mas o programa não informa sucesso quando há erro.

## Resumo para o grupo

Ajustei para cada marcação receber um ID próprio, mantendo o código do colaborador fixo. Entrada, pausa e saída ficam em eventos separados, com tipo e dataHora, sem apagar os anteriores. Adaptei o TesteJackson para receber o código e a ação, mantive a integração nas classes reais e os contratos por interface. Os blocos estão comentados e os arquivos anteriores foram preservados separadamente.