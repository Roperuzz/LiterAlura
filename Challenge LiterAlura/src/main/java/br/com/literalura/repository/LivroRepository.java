package br.com.literalura.repository;

import br.com.literalura.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    /**
     * Busca um livro pelo título (case-sensitive).
     *
     * @param titulo Título do livro.
     * @return Um Optional contendo o livro encontrado ou vazio, se não encontrado.
     */
    Optional<Livro> findByTitulo(String titulo);

    /**
     * Busca livros cujo título contém a parte especificada (case-insensitive).
     *
     * @param titulo Parte do título do livro.
     * @return Lista de livros cujo título contém o texto especificado.
     */
    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    /**
     * Busca livros por idioma.
     *
     * @param idioma Código do idioma.
     * @return Lista de livros no idioma especificado.
     */
    List<Livro> findByIdioma(String idioma);

    /**
     * Busca livros de um autor específico.
     *
     * @param autorId ID do autor.
     * @return Lista de livros do autor especificado.
     */
    List<Livro> findByAutor_Id(Long autorId);

    /**
     * Busca todos os livros com paginação e ordenação.
     *
     * @param pageable Objeto de paginação e ordenação.
     * @return Página de livros.
     */
    Page<Livro> findAll(Pageable pageable);
}

