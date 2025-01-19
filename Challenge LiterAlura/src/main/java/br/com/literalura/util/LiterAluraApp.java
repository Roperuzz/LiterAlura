package br.com.literalura.util;

import java.util.Scanner;

public class LiterAluraApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Buscar livro pelo título");
            System.out.println("2 - Listar livros registrados");
            System.out.println("3 - Listar autores");
            System.out.println("4 - Listar autores por ano");
            System.out.println("5 - Listar livros por idioma");
            System.out.println("0 - Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    System.out.println("Digite o título do livro:");
                    String titulo = scanner.nextLine();
                    // Implementar busca na API e salvar no banco
                    break;
                case 2:
                    // Implementar listagem de livros
                    break;
                case 3:
                    // Implementar listagem de autores
                    break;
                case 4:
                    // Implementar filtro por ano
                    break;
                case 5:
                    // Implementar filtro por idioma
                    break;
                case 0:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
