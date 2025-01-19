package br.com.literalura.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Component
public class GutendexClient {

    private static final Logger logger = LoggerFactory.getLogger(GutendexClient.class);
    private static final String BASE_URL = "https://gutendex.com/books?search=";

    public String buscarLivroPorTitulo(String titulo) {
        validarTitulo(titulo);

        try {
            // Codifica o título para evitar problemas com caracteres especiais
            String tituloCodificado = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
            String url = BASE_URL + tituloCodificado;

            // Cria o cliente HTTP
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(java.time.Duration.ofSeconds(10))
                    .build();

            // Cria a requisição HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .timeout(java.time.Duration.ofSeconds(10))
                    .build();

            logger.info("Enviando requisição para a URL: {}", url);

            // Envia a requisição e obtém a resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica o status HTTP da resposta
            if (response.statusCode() != 200) {
                logger.error("Erro ao buscar livro na API. Status: {}, Corpo: {}", response.statusCode(), response.body());
                throw new RuntimeException("Erro ao buscar livro na API. Status HTTP: " + response.statusCode());
            }

            logger.info("Resposta recebida com sucesso.");
            return response.body();

        } catch (Exception e) {
            logger.error("Erro ao buscar livro na API: {}", e.getMessage());
            throw new RuntimeException("Erro ao buscar livro na API: " + e.getMessage(), e);
        }
    }

    private void validarTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            logger.error("O título fornecido está vazio ou nulo.");
            throw new IllegalArgumentException("O título do livro é obrigatório.");
        }
    }
}

