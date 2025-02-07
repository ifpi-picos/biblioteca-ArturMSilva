package com.biblioteca.dao;

import com.biblioteca.entity.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {

    private Connection conexao;

    public EnderecoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Método para inserir um novo endereço
    public void inserir(Endereco endereco) throws SQLException {
        String sql = "INSERT INTO endereco (rua, cep, numero, bairro, cidade, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, endereco.getRua());
            statement.setString(2, endereco.getCep());
            statement.setInt(3, endereco.getNumero());
            statement.setString(4, endereco.getBairro());
            statement.setString(5, endereco.getCidade());
            statement.setString(6, endereco.getEstado());

            statement.executeUpdate();

            // Recuperar o ID gerado automaticamente
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    endereco.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    // Método para buscar um endereço pelo ID
    public Endereco buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM endereco WHERE id = ?";
        Endereco endereco = null;

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    endereco = new Endereco(
                        resultSet.getString("rua"),
                        resultSet.getString("cep"),
                        resultSet.getInt("numero"),
                        resultSet.getString("bairro"),
                        resultSet.getString("cidade"),
                        resultSet.getString("estado")
                    );
                    endereco.setId(resultSet.getInt("id"));
                }
            }
        }
        return endereco;
    }

    // Método para listar todos os endereços
    public List<Endereco> listarTodos() throws SQLException {
        String sql = "SELECT * FROM endereco";
        List<Endereco> enderecos = new ArrayList<>();

        try (PreparedStatement statement = conexao.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Endereco endereco = new Endereco(
                    resultSet.getString("rua"),
                    resultSet.getString("cep"),
                    resultSet.getInt("numero"),
                    resultSet.getString("bairro"),
                    resultSet.getString("cidade"),
                    resultSet.getString("estado")
                );
                endereco.setId(resultSet.getInt("id"));
                enderecos.add(endereco);
            }
        }
        return enderecos;
    }

    // Método para atualizar um endereço
    public void atualizar(Endereco endereco) throws SQLException {
        String sql = "UPDATE endereco SET rua = ?, cep = ?, numero = ?, bairro = ?, cidade = ?, estado = ? WHERE id = ?";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, endereco.getRua());
            statement.setString(2, endereco.getCep());
            statement.setInt(3, endereco.getNumero());
            statement.setString(4, endereco.getBairro());
            statement.setString(5, endereco.getCidade());
            statement.setString(6, endereco.getEstado());
            statement.setInt(7, endereco.getId());

            statement.executeUpdate();
        }
    }

    // Método para excluir um endereço
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM endereco WHERE id = ?";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}