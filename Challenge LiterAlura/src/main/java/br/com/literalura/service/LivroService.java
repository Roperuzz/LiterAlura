package br.com.literalura.service;

import br.com.literalura.dto.LivroDTO;
import br.com.literalura.model.Autor;
import br.com.literalura.model.Livro;
import br.com.literalura.repository.AutorRepository;
import br.com.literalura.repository.LivroRepository;
import br.com.literalura.util.GutendexClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private static final Logger logger = LoggerFactory.getLogger(LivroService.class);

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final GutendexClient gutendexClient;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, GutendexClient gutendexClient) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.gutendexClient = gutendexClient;
    }

    // Buscar livro por título no banco de dados
    public List<Livro> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    // Listar todos os livros registrados no banco
    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    // Buscar livros por idioma
    public List<Livro> buscarPorIdioma(String idioma) {
        return livroRepository.findByIdioma(idioma);
    }

    // Método para buscar livro por título da API externa e salvar no banco
    public List<Livro> buscarLivroPorTitulo(String titulo) {
        try {
            logger.info("Iniciando busca do(s) livro(s) com título: {}", titulo);

            // Chama o cliente para buscar o JSON da API
            String respostaJson = gutendexClient.buscarLivroPorTitulo(titulo);

            // Valida a resposta da API
            validarRespostaApi(respostaJson);

            // Mapeia a resposta JSON para um array de LivroDTO
            ObjectMapper mapper = new ObjectMapper();
            LivroDTO[] livrosDto = mapper.readValue(respostaJson, LivroDTO[].class);

            // Valida se algum livro foi encontrado
            validarLivrosEncontrados(livrosDto);

            // Converte os DTOs para entidades Livro, salva no banco e retorna
            List<Livro> livros = Arrays.stream(livrosDto)
                    .map(this::converterParaLivro)
                    .map(livroRepository::save)
                    .collect(Collectors.toList());

            logger.info("Livros salvos com sucesso: {}", livros);
            return livros;

        } catch (RuntimeException e) {
            logger.error("Erro ao buscar livro: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao buscar livro por título: {}", e.getMessage());
            throw new RuntimeException("Erro inesperado ao buscar livro por título.", e);
        }
    }

    private void validarRespostaApi(String respostaJson) {
        if (respostaJson == null || respostaJson.isBlank()) {
            logger.error("A API retornou uma resposta vazia ou inválida.");
            throw new ApiResponseException("A API retornou uma resposta vazia ou inválida.");
        }
    }

    private void validarLivrosEncontrados(LivroDTO[] livros) {
        if (livros == null || livros.length == 0) {
            logger.error("Nenhum livro encontrado com o título informado.");
            throw new LivroNaoEncontradoException("Nenhum livro foi encontrado com o título informado.");
        }
    }

    private Livro converterParaLivro(LivroDTO livroDTO) {
        // Verifica ou cria o autor
        Autor autor = recuperarOuCriarAutor(livroDTO.getAuthors());

        return new Livro(
                livroDTO.getTitle(),
                livroDTO.getLanguages().length > 0 ? livroDTO.getLanguages()[0] : "Idioma desconhecido",
                autor
        );
    }

    private Autor recuperarOuCriarAutor(String[] authors) {
        if (authors == null || authors.length == 0) {
            logger.warn("Nenhum autor informado. Criando um autor genérico.");
            return autorRepository.save(new Autor("Autor desconhecido", null, null));
        }

        String nomeAutor = authors[0]; // Considerando apenas o primeiro autor
        return autorRepository.findByNome(nomeAutor)
                .orElseGet(() -> {
                    logger.info("Criando novo autor: {}", nomeAutor);
                    return autorRepository.save(new Autor(nomeAutor, null, null));
                });
    }

    // Exceção personalizada para erros da API
    private static class ApiResponseException extends RuntimeException {
        public ApiResponseException(String message) {
            super(message);
        }
    }

    // Exceção personalizada para livros não encontrados
    private static class LivroNaoEncontradoException extends RuntimeException {
        public LivroNaoEncontradoException(String message) {
            super(message);
        }
    }
}
