package br.com.literalura.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O título do livro é obrigatório")
    @Size(min = 2, max = 200, message = "O título deve ter entre 2 e 200 caracteres")
    private String titulo;

    @NotNull(message = "O idioma do livro é obrigatório")
    @Size(min = 2, max = 3, message = "O idioma deve ser um código ISO de 2 ou 3 caracteres")
    private String idioma;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    @NotNull(message = "O autor do livro é obrigatório")
    private Autor autor;

    // Construtor padrão (necessário para o JPA)
    public Livro() {
    }

    // Construtor com argumentos
    public Livro(String titulo, String idioma, Autor autor) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título do livro não pode ser vazio.");
        }
        if (idioma == null || idioma.trim().length() < 2 || idioma.trim().length() > 3) {
            throw new IllegalArgumentException("O idioma deve ser um código ISO de 2 ou 3 caracteres.");
        }
        if (autor == null) {
            throw new IllegalArgumentException("O autor do livro é obrigatório.");
        }
        this.titulo = titulo;
        this.idioma = idioma;
        this.autor = autor;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    // Override de toString para facilitar logs e depuração
    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", autor=" + (autor != null ? autor.getNome() : "N/A") +
                '}';
    }

    // Override de equals e hashCode para comparação baseada no ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Livro livro = (Livro) o;

        return id != null && id.equals(livro.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
