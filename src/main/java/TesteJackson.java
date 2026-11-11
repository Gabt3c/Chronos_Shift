/* 
esta classe é um experimento de persistência simples(ou não tão simples)
que visa testar a implentação de um JSON como meio de persistir o registro de ponto
*/

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class TesteJackson {

    // Objeto pequeno para testes, contendo apenas valores essenciais
    public record RegistroPonto(
        int id,
        String tipo,
        LocalDateTime dataHora
    ) {}

    public static void main(String[] args) throws Exception {

        // 1. Configura Jackson
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 2. Cria um objeto para registrar ponto, ATENÇÃO PARA ISSO
        RegistroPonto registro = new RegistroPonto(
            1,
            "ENTRADA",
            LocalDateTime.now()
        );

        // 3. Aponta o diretório onde o arquivo será armazenado
        Path directory = Path.of("data");
        Files.createDirectories(directory);

        File file = directory.resolve("teste-registro.json").toFile();

        // 4. Muda de java para Json || Java -> JSON
        mapper.writeValue(file, registro);

        System.out.println("JSON successfully saved!");//Só um retorno de que a parada funfou =D

        // 5. Recupera de Json para Java || JSON -> Java
        RegistroPonto recuperado = mapper.readValue(
            file,
            RegistroPonto.class
        );

        System.out.println("Recovered Java object:");
        System.out.println(recuperado);
    }
}

/* 
########################
##CONSIDERAÇÕES FINAIS## 
########################

É perfeitamente plausível usarmos isso, tenho que admitir que não é a melhor solução e que o único 
motivo para estarmos usando isso é pelo tempo apertado, mas se eu consegui resolver isso numa madrugada,
vocês também conseguem. Se atentem às orientações que vou deixar no WhatsApp e vai dar tudo certo.
*/