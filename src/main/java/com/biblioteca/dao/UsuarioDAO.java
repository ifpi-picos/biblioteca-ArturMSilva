package com.biblioteca.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.biblioteca.entity.Usuario;


public class UsuarioDAO {
    private Connection conexao;

    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Método para inserir um novo usuário
    public void inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, cpf, email, endereco_id) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getCpf());
        stmt.setString(3, usuario.getEmail());
        stmt.setInt(4, usuario.getEnderecoId());
        stmt.executeUpdate();

        stmt.close();
    }

    // Método para buscar todos os usuários
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        Statement stmt = conexao.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setCpf(rs.getString("cpf"));
            usuario.setEmail(rs.getString("email"));
            usuarios.add(usuario);
        }
        stmt.close();
        return usuarios;
    }

    // Método para buscar um usuário por ID
    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setCpf(rs.getString("cpf"));
            usuario.setEmail(rs.getString("email"));
            return usuario;
        }
        return null;
    }

    // Método para atualizar um usuário
    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, cpf = ?, email = ? WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getCpf());
        stmt.setString(3, usuario.getEmail());
        stmt.setInt(4, usuario.getId());
        stmt.executeUpdate();
        stmt.close();
    }

    // Método para deletar um usuário pelo ID
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
    }
}
