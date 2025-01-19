package br.com.literalura.service;

import br.com.literalura.model.Autor;
import br.com.literalura.repository.AutorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    private static final Logger logger = LoggerFactory.getLogger(AutorService.class);

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor salvarOuBuscarPorNome(String nome) {
        validarNome(nome);
        logger.info("Buscando autor pelo nome: {}", nome);
        return autorRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    logger.info("Autor não encontrado. Criando novo autor: {}", nome);
                    Autor novoAutor = new Autor(nome.trim(), null, null);
                    return salvar(novoAutor);
                });
    }

    public Autor salvar(Autor autor) {
        validarAutor(autor);
        logger.info("Salvando autor: {}", autor.getNome());
        return autorRepository.save(autor);
    }

    public List<Autor> listarTodos() {
        logger.info("Listando todos os autores registrados no banco de dados.");
        return autorRepository.findAll();
    }

    public Page<Autor> listarTodosPaginados(Pageable pageable) {
        logger.info("Listando todos os autores com paginação.");
        return autorRepository.findAll(pageable);
    }

    public Autor buscarPorId(Long id) {
        if (id == null) {
            logger.error("Tentativa de busca com ID nulo.");
            throw new IllegalArgumentException("O ID do autor é obrigatório.");
        }
        logger.info("Buscando autor pelo ID: {}", id);
        return autorRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Autor não encontrado com o ID: {}", id);
                    return new IllegalArgumentException("Autor não encontrado com o ID: " + id);
                });
    }

    public List<Autor> buscarPorNome(String nome) {
        validarNome(nome);
        logger.info("Buscando autores com nome contendo: {}", nome);
        return autorRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Autor> buscarAutoresVivosNoAno(int anoReferencia) {
        if (anoReferencia < 0) {
            logger.error("Ano de referência inválido fornecido: {}", anoReferencia);
            throw new IllegalArgumentException("O ano de referência deve ser um valor positivo.");
        }
        logger.info("Buscando autores vivos no ano de referência: {}", anoReferencia);
        return autorRepository.findByAnoNascimentoLessThanEqualAndAnoFalecimentoIsNullOrAnoFalecimentoGreaterThanEqual(
                anoReferencia, anoReferencia
        );
    }

    public Autor atualizar(Long id, Autor autorAtualizado) {
        if (id == null) {
            logger.error("Tentativa de atualização com ID nulo.");
            throw new IllegalArgumentException("O ID do autor é obrigatório.");
        }
        validarAutor(autorAtualizado);
        logger.info("Atualizando autor com ID: {}", id);
        return autorRepository.findById(id).map(autor -> {
            autor.setNome(autorAtualizado.getNome());
            autor.setAnoNascimento(autorAtualizado.getAnoNascimento());
            autor.setAnoFalecimento(autorAtualizado.getAnoFalecimento());
            return autorRepository.save(autor);
        }).orElseThrow(() -> {
            logger.error("Autor não encontrado com o ID: {}", id);
            return new IllegalArgumentException("Autor não encontrado com o ID: " + id);
        });
    }

    private void validarAutor(Autor autor) {
        if (autor == null || autor.getNome() == null || autor.getNome().isBlank()) {
            logger.error("Tentativa de salvar ou atualizar um autor inválido.");
            throw new IllegalArgumentException("O autor deve ter um nome válido.");
        }
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            logger.error("Tentativa de busca com nome vazio.");
            throw new IllegalArgumentException("O nome do autor é obrigatório.");
        }
    }
}
