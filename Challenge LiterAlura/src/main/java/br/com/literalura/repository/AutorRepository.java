package br.com.literalura.repository;

import br.com.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    /**
     * Busca um autor pelo nome.
     *
     * @param nome Nome do autor.
     * @return Um Optional contendo o autor encontrado ou vazio, se não encontrado.
     */
    Optional<Autor> findByNome(String nome);

    /**
     * Busca autores cujo nome contém o texto especificado (case-insensitive).
     *
     * @param nome Parte do nome do autor.
     * @return Lista de autores cujo nome contém o texto especificado.
     */
    List<Autor> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca autores com base no ano de nascimento e falecimento.
     *
     * @param anoNascimento  Ano de nascimento do autor.
     * @param anoFalecimento Ano de falecimento do autor.
     * @return Lista de autores que atendem ao critério.
     */
    List<Autor> findByAnoNascimentoLessThanEqualAndAnoFalecimentoIsNullOrAnoFalecimentoGreaterThanEqual(
            int anoNascimento, int anoFalecimento);
}

