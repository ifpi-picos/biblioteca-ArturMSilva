package com.biblioteca;

import com.biblioteca.dao.EmprestimoDAO;
import com.biblioteca.dao.EnderecoDAO;
import com.biblioteca.dao.LivroDAO;
import com.biblioteca.dao.UsuarioDAO;
import com.biblioteca.entity.Emprestimo;
import com.biblioteca.entity.Endereco;
import com.biblioteca.entity.Livro;
import com.biblioteca.entity.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App {
    private static Connection conexao;
    private static LivroDAO livroDAO;
    private static UsuarioDAO usuarioDAO;
    private static EnderecoDAO enderecoDAO;
    private static EmprestimoDAO emprestimoDAO;

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/biblioteca";
        String usuario = "postgres";
        String senha = "root21476601";

        try {
            // Estabelecer conexão com o banco de dados
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão com o banco de dados estabelecida!");

            // Inicializar DAOs
            livroDAO = new LivroDAO(conexao);
            usuarioDAO = new UsuarioDAO(conexao);
            enderecoDAO = new EnderecoDAO(conexao);
            emprestimoDAO = new EmprestimoDAO(conexao);

            // Exibir menu principal
            exibirMenu();

        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        } finally {
            // Fechar conexão ao finalizar o programa
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar a conexão: " + e.getMessage());
                }
            }
        }
    }

    private static void exibirMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== MENU DA BIBLIOTECA ===");
            System.out.println("1. Cadastrar novo livro");
            System.out.println("2. Listar todos os livros");
            System.out.println("3. Realizar empréstimo de livro");
            System.out.println("4. Devolver livro");
            System.out.println("5. Cadastrar novo usuário");
            System.out.println("6. Listar todos os usuários");
            System.out.println("7. Cadastrar novo endereço");
            System.out.println("8. Listar todos os endereços");
            System.out.println("9. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    cadastrarLivro(scanner);
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    realizarEmprestimo(scanner);
                    break;
                case 4:
                    devolverLivro(scanner);
                    break;
                case 5:
                    cadastrarUsuario(scanner);
                    break;
                case 6:
                    listarUsuarios();
                    break;
                case 7:
                    cadastrarEndereco(scanner);
                    break;
                case 8:
                    listarEnderecos();
                    break;
                case 9:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 9);

        scanner.close();
    }

    private static void cadastrarLivro(Scanner scanner) throws SQLException {
        System.out.println("\n=== CADASTRAR LIVRO ===");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Nome do autor: ");
        String nomeAutor = scanner.nextLine();
        System.out.print("Ano de publicação: ");
        int anoPublicacao = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        System.out.print("Quantidade de exemplares: ");
        int quantidadeExemplares = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        System.out.print("Gênero: ");
        String genero = scanner.nextLine();

        Livro livro = new Livro(titulo, nomeAutor, anoPublicacao, quantidadeExemplares, genero, false);
        livroDAO.inserir(livro);
        System.out.println("Livro cadastrado com sucesso!");
    }

    private static void listarLivros() throws SQLException {
        System.out.println("\n=== LISTAR LIVROS ===");
        List<Livro> livros = livroDAO.listarTodos();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            for (Livro livro : livros) {
                System.out.println(livro);
            }
        }
    }

    private static void realizarEmprestimo(Scanner scanner) throws SQLException {
        System.out.println("\n=== REALIZAR EMPRÉSTIMO ===");
        System.out.print("ID do usuário: ");
        int usuarioId = scanner.nextInt();
        System.out.print("ID do livro: ");
        int livroId = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        Livro livro = livroDAO.buscarPorId(livroId);
        if (livro == null || livro.isEmprestado()) {
            System.out.println("Livro não disponível para empréstimo.");
            return;
        }

        Date dataEmprestimo = Date.valueOf(LocalDate.now());
        Date dataDevolucao = Date.valueOf(LocalDate.now().plusDays(7)); // Prazo de 7 dias

        Emprestimo emprestimo = new Emprestimo(usuarioId, livroId, dataEmprestimo, dataDevolucao, false);
        emprestimoDAO.inserir(emprestimo);
        livroDAO.marcarComoEmprestado(livroId, true);
        System.out.println("Empréstimo realizado com sucesso!");
    }

    private static void devolverLivro(Scanner scanner) throws SQLException {
        System.out.println("\n=== DEVOLVER LIVRO ===");
        System.out.print("ID do empréstimo: ");
        int emprestimoId = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        Emprestimo emprestimo = emprestimoDAO.buscarPorId(emprestimoId);
        if (emprestimo == null || emprestimo.isDevolvido()) {
            System.out.println("Empréstimo não encontrado ou já devolvido.");
            return;
        }

        emprestimo.setDevolvido(true);
        emprestimoDAO.atualizar(emprestimo);
        livroDAO.marcarComoEmprestado(emprestimo.getLivroId(), false);
        System.out.println("Livro devolvido com sucesso!");
    }

    private static void cadastrarUsuario(Scanner scanner) throws SQLException {
        System.out.println("\n=== CADASTRAR USUÁRIO ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("ID do endereço: ");
        int enderecoId = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        Usuario usuario = new Usuario(nome, cpf, email, enderecoId);
        usuarioDAO.inserir(usuario);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    private static void listarUsuarios() throws SQLException {
        System.out.println("\n=== LISTAR USUÁRIOS ===");
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            for (Usuario usuario : usuarios) {
                System.out.println(usuario);
            }
        }
    }

    private static void cadastrarEndereco(Scanner scanner) throws SQLException {
        System.out.println("\n=== CADASTRAR ENDEREÇO ===");
        System.out.print("Rua: ");
        String rua = scanner.nextLine();
        System.out.print("CEP: ");
        String cep = scanner.nextLine();
        System.out.print("Número: ");
        int numero = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();

        Endereco endereco = new Endereco(rua, cep, numero, bairro, cidade, estado);
        enderecoDAO.inserir(endereco);
        System.out.println("Endereço cadastrado com sucesso!");
    }

    private static void listarEnderecos() throws SQLException {
        System.out.println("\n=== LISTAR ENDEREÇOS ===");
        List<Endereco> enderecos = enderecoDAO.listarTodos();
        if (enderecos.isEmpty()) {
            System.out.println("Nenhum endereço cadastrado.");
        } else {
            for (Endereco endereco : enderecos) {
                System.out.println(endereco);
            }
        }
    }
}