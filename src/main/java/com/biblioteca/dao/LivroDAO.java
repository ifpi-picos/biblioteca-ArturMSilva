package com.biblioteca.dao;

import com.biblioteca.entity.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    private Connection conexao;

    // Construtor que recebe a conexão com o banco de dados
    public LivroDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Método para inserir um novo livro no banco de dados
    public void inserir(Livro livro) throws SQLException {
        String sql = "INSERT INTO livro (titulo, nome_autor, ano_publicacao, quantidade_exemplares, genero, emprestado) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, livro.getTitulo());
            statement.setString(2, livro.getNomeAutor());
            statement.setInt(3, livro.getAnoPublicacao());
            statement.setInt(4, livro.getQuantidadeExemplares());
            statement.setString(5, livro.getGenero());
            statement.setBoolean(6, livro.isEmprestado());

            statement.executeUpdate();

            // Recuperar o ID gerado automaticamente
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    livro.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    // Método para buscar um livro pelo ID
    public Livro buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM livro WHERE id = ?";
        Livro livro = null;

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    livro = new Livro(
                        resultSet.getInt("id"),
                        resultSet.getString("titulo"),
                        resultSet.getString("nome_autor"),
                        resultSet.getInt("ano_publicacao"),
                        resultSet.getInt("quantidade_exemplares"),
                        resultSet.getString("genero"),
                        resultSet.getBoolean("emprestado")
                    );
                }
            }
        }
        return livro;
    }

    // Método para listar todos os livros
    public List<Livro> listarTodos() throws SQLException {
        String sql = "SELECT * FROM livro";
        List<Livro> livros = new ArrayList<>();

        try (PreparedStatement statement = conexao.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Livro livro = new Livro(
                    resultSet.getInt("id"),
                    resultSet.getString("titulo"),
                    resultSet.getString("nome_autor"),
                    resultSet.getInt("ano_publicacao"),
                    resultSet.getInt("quantidade_exemplares"),
                    resultSet.getString("genero"),
                    resultSet.getBoolean("emprestado")
                );
                livros.add(livro);
            }
        }
        return livros;
    }

    // Método para atualizar um livro existente
    public void atualizar(Livro livro) throws SQLException {
        String sql = "UPDATE livro SET titulo = ?, nome_autor = ?, ano_publicacao = ?, quantidade_exemplares = ?, genero = ?, emprestado = ? WHERE id = ?";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, livro.getTitulo());
            statement.setString(2, livro.getNomeAutor());
            statement.setInt(3, livro.getAnoPublicacao());
            statement.setInt(4, livro.getQuantidadeExemplares());
            statement.setString(5, livro.getGenero());
            statement.setBoolean(6, livro.isEmprestado());
            statement.setInt(7, livro.getId());

            statement.executeUpdate();
        }
    }

    // Método para excluir um livro pelo ID
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM livro WHERE id = ?";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Método para verificar se um livro está disponível para empréstimo
    public boolean estaDisponivel(int id) throws SQLException {
        String sql = "SELECT emprestado FROM livro WHERE id = ?";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return !resultSet.getBoolean("emprestado");
                }
            }
        }
        return false; // Se o livro não for encontrado, assume-se que não está disponível
    }

    // Método para marcar um livro como emprestado ou devolvido
    public void marcarComoEmprestado(int id, boolean emprestado) throws SQLException {
        String sql = "UPDATE livro SET emprestado = ? WHERE id = ?";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setBoolean(1, emprestado);
            statement.setInt(2, id);

            statement.executeUpdate();
        }
    }
}