package br.com.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroDTO {

    private String title;
    private String[] authors;
    private String[] languages;

    // Construtor padrão
    public LivroDTO() {
        this.authors = new String[0];
        this.languages = new String[0];
    }

    // Construtor com argumentos
    public LivroDTO(String title, String[] authors, String[] languages) {
        this.title = title;
        this.authors = authors != null ? authors : new String[0];
        this.languages = languages != null ? languages : new String[0];
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAuthors() {
        return authors != null ? authors : new String[0];
    }

    public void setAuthors(String[] authors) {
        this.authors = authors != null ? authors : new String[0];
    }

    public String[] getLanguages() {
        return languages != null ? languages : new String[0];
    }

    public void setLanguages(String[] languages) {
        this.languages = languages != null ? languages : new String[0];
    }

    @Override
    public String toString() {
        return "LivroDTO{" +
                "title='" + title + '\'' +
                ", authors=" + Arrays.toString(authors) +
                ", languages=" + Arrays.toString(languages) +
                '}';
    }
}
