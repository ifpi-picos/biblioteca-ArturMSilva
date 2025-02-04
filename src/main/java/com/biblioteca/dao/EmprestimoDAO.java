package com.biblioteca.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.biblioteca.entity.Emprestimo;

public class EmprestimoDAO {
    private Connection conexao;

    public EmprestimoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Emprestimo emprestimo) throws SQLException {
        String sql = "INSERT INTO emprestimo (usuarioId, livroId, dataEmprestimo, dataDevolucao, devolvido) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, emprestimo.getUsuarioId());
        stmt.setInt(2, emprestimo.getLivroId());
        stmt.setDate(3, emprestimo.getDataEmprestimo());
        stmt.setDate(4, emprestimo.getDataDevolucao());
        stmt.setBoolean(5, emprestimo.isDevolvido());
        stmt.executeUpdate();

        stmt.close();
    }

    public Emprestimo buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM emprestimo WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        Emprestimo emprestimo = null;
        if (rs.next()) {
            emprestimo = new Emprestimo(
                rs.getInt("usuarioId"),
                rs.getInt("livroId"),
                rs.getDate("dataEmprestimo"),
                rs.getDate("dataDevolucao"),
                rs.getBoolean("devolvido")
            );
        }

        rs.close();
        stmt.close();
        return emprestimo;
    }

    public void atualizar(Emprestimo emprestimo) throws SQLException {
        String sql = "UPDATE emprestimo SET usuarioId = ?, livroId = ?, dataEmprestimo = ?, dataDevolucao = ?, devolvido = ? WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, emprestimo.getUsuarioId());
        stmt.setInt(2, emprestimo.getLivroId());
        stmt.setDate(3, emprestimo.getDataEmprestimo());
        stmt.setDate(4, emprestimo.getDataDevolucao());
        stmt.setBoolean(5, emprestimo.isDevolvido());
        stmt.setInt(6, emprestimo.getId());
        stmt.executeUpdate();

        stmt.close();
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM emprestimo WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();

        stmt.close();
    }

    public List<Emprestimo> listarTodos() throws SQLException {
        String sql = "SELECT * FROM emprestimo";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Emprestimo> emprestimos = new ArrayList<>();
        while (rs.next()) {
            Emprestimo emprestimo = new Emprestimo(
                rs.getInt("usuarioId"),
                rs.getInt("livroId"),
                rs.getDate("dataEmprestimo"),
                rs.getDate("dataDevolucao"),
                rs.getBoolean("devolvido")
            );
            emprestimos.add(emprestimo);
        }

        rs.close();
        stmt.close();
        return emprestimos;
    }

}
