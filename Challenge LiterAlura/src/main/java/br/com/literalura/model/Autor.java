package br.com.literalura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome do autor é obrigatório")
    @Size(min = 2, max = 100, message = "O nome do autor deve ter entre 2 e 100 caracteres")
    private String nome;

    @Min(value = 0, message = "O ano de nascimento deve ser um valor positivo")
    private Integer anoNascimento;

    @Min(value = 0, message = "O ano de falecimento deve ser um valor positivo")
    private Integer anoFalecimento;

    // Construtor padrão
    public Autor() {
    }

    // Construtor com argumentos
    public Autor(String nome, Integer anoNascimento, Integer anoFalecimento) {
        this.nome = nome;
        this.anoNascimento = anoNascimento;

        if (anoFalecimento != null && anoNascimento != null && anoFalecimento < anoNascimento) {
            throw new IllegalArgumentException("O ano de falecimento não pode ser menor que o ano de nascimento");
        }
        this.anoFalecimento = anoFalecimento;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        if (anoFalecimento != null && anoNascimento != null && anoFalecimento < anoNascimento) {
            throw new IllegalArgumentException("O ano de falecimento não pode ser menor que o ano de nascimento");
        }
        this.anoFalecimento = anoFalecimento;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", anoNascimento=" + (anoNascimento != null ? anoNascimento : "não informado") +
                ", anoFalecimento=" + (anoFalecimento != null ? anoFalecimento : "não informado") +
                '}';
    }
}
