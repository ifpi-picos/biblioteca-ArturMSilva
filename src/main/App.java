package main;

import model.Biblioteca;
import model.Livro;
import model.Usuario;
import model.Emprestimo;

import java.time.LocalDate;
import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);
    static Biblioteca biblioteca = new Biblioteca();

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n=== Sistema de Biblioteca ===");
            System.out.println("1. Cadastrar Livro");
            System.out.println("2. Listar Todos os Livros");
            System.out.println("3. Listar Livros Emprestados e Disponíveis");
            System.out.println("4. Histórico de Empréstimos do Usuário");
            System.out.println("5. Pegar Livro Emprestado");
            System.out.println("6. Devolver Livro");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> cadastrarLivro();
                case 2 -> listarTodosLivros();
                case 3 -> listarLivrosEmprestadosEDisponiveis();
                case 4 -> historicoEmprestimosUsuario();
                case 5 -> pegarLivroEmprestado();
                case 6 -> devolverLivro();
                case 0 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
        scanner.close();
    }

    // Função para cadastrar um livro
    private static void cadastrarLivro() {
        System.out.println("\n=== Cadastrar Livro ===");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Nome do Autor: ");
        String autor = scanner.nextLine();

        System.out.print("Ano de Publicação: ");
        int ano = Integer.parseInt(scanner.nextLine());

        System.out.print("Quantidade de Exemplares: ");
        int quantidade = Integer.parseInt(scanner.nextLine());

        System.out.print("Gênero: ");
        String genero = scanner.nextLine();

        Livro livro = new Livro(titulo, autor, ano, quantidade, genero);
        biblioteca.livros.add(livro);
        System.out.println("Livro cadastrado com sucesso!");
    }

    // Função para listar todos os livros
    private static void listarTodosLivros() {
        System.out.println("\n=== Lista de Todos os Livros ===");
        for (Livro livro : biblioteca.livros) {
            System.out.println("Título: " + livro.titulo + "\nAutor: " + livro.nomeAutor +
                    "\nAno: " + livro.anoPublicacao + "\nGênero: " + livro.genero +
                    "\nExemplares: " + livro.quantidadeExemplares +
                    "\nEmprestado: " + (livro.emprestado ? "Sim" : "Não") + "\n");
        }
    }

    // Função para listar livros emprestados e disponíveis
    private static void listarLivrosEmprestadosEDisponiveis() {
        System.out.println("\n=== Livros Emprestados ===");
        biblioteca.livros.stream()
                .filter(l -> l.emprestado)
                .forEach(l -> System.out.println("Título: " + l.titulo));

        System.out.println("\n=== Livros Disponíveis ===");
        biblioteca.livros.stream()
                .filter(l -> !l.emprestado)
                .forEach(l -> System.out.println("Título: " + l.titulo));
    }

    // Função para pegar um livro emprestado
    private static void pegarLivroEmprestado() {
        System.out.println("\n=== Pegar Livro Emprestado ===");
        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();

        Livro livroEncontrado = biblioteca.livros.stream()
                .filter(l -> l.titulo.equalsIgnoreCase(titulo) && !l.emprestado)
                .findFirst()
                .orElse(null);

        if (livroEncontrado == null) {
            System.out.println("Livro não encontrado ou já emprestado.");
            return;
        }

        System.out.print("Nome do usuário: ");
        String nomeUsuario = scanner.nextLine();

        Usuario usuario = new Usuario(nomeUsuario);
        biblioteca.usuarios.add(usuario);

        Emprestimo emprestimo = new Emprestimo(usuario, livroEncontrado, LocalDate.now(), null);
        biblioteca.emprestimos.add(emprestimo);
        livroEncontrado.emprestado = true;

        System.out.println("Livro emprestado com sucesso!");
    }

    // Função para devolver um livro
    private static void devolverLivro() {
        System.out.println("\n=== Devolver Livro ===");
        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();

        Emprestimo emprestimo = biblioteca.emprestimos.stream()
                .filter(e -> e.livro.titulo.equalsIgnoreCase(titulo) && !e.devolvido)
                .findFirst()
                .orElse(null);

        if (emprestimo == null) {
            System.out.println("Empréstimo não encontrado.");
            return;
        }

        emprestimo.devolvido = true;
        emprestimo.livro.emprestado = false;
        emprestimo.dataDevolucao = LocalDate.now();
        System.out.println("Livro devolvido com sucesso!");
    }

    // Histórico de empréstimos do usuário
    private static void historicoEmprestimosUsuario() {
        System.out.println("\n=== Histórico de Empréstimos do Usuário ===");
        System.out.print("Nome do usuário: ");
        String nomeUsuario = scanner.nextLine();

        biblioteca.emprestimos.stream()
                .filter(e -> e.usuario.nome.equalsIgnoreCase(nomeUsuario))
                .forEach(e -> System.out.println("Título: " + e.livro.titulo +
                        ", Data de Empréstimo: " + e.dataEmprestimo +
                        ", Data de Devolução: " + (e.dataDevolucao != null ? e.dataDevolucao : "Ainda emprestado")));
    }
}
