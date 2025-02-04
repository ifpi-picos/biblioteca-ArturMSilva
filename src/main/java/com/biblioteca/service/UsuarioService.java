package com.biblioteca.service;

import java.sql.SQLException;

import com.biblioteca.dao.UsuarioDAO;
import com.biblioteca.entity.Usuario;

public class UsuarioService {
    private UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public void adicionarUsuario(Usuario usuario) {
        try {
            validarUsuario(usuario); // Validações antes de inserir
            usuarioDAO.inserir(usuario);
            System.out.println("Usuário inserido com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário no banco: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Dados inválidos: " + e.getMessage());
        }
    }

    private void validarUsuario(Usuario usuario) {
        validarNome(usuario.getNome());
        validarCPF(usuario.getCpf());
        validarEmail(usuario.getEmail());
        validarEnderecoId(usuario.getEnderecoId());
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (nome.length() > 100) {
            throw new IllegalArgumentException("Nome excede o limite de 100 caracteres");
        }
        if (!nome.matches("^[\\p{L} ]+$")) { // Apenas letras e espaços
            throw new IllegalArgumentException("Nome contém caracteres inválidos");
        }
    }

    private void validarCPF(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos");
        }
    }

    // Validação de e-mail (formato básico)
    private void validarEmail(String email) {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")) {
            throw new IllegalArgumentException("Formato de e-mail inválido");
        }
    }

    private void validarEnderecoId(int enderecoId) {
        if (enderecoId <= 0) {
            throw new IllegalArgumentException("ID do endereço deve ser positivo");
        }
    }
}