package br.com.literalura;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication {

    private static final Logger logger = LoggerFactory.getLogger(LiterAluraApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(LiterAluraApplication.class, args);
            logger.info("Aplicação LiterAlura iniciada com sucesso!");
        } catch (Exception e) {
            logger.error("Erro ao iniciar a aplicação LiterAlura: {}", e.getMessage(), e);
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("Aplicação LiterAlura está sendo encerrada...");
            }));
        }
    }
}
