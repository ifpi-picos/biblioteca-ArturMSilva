package com.biblioteca;

import java.sql.*;
import java.util.Scanner;

import com.biblioteca.dao.UsuarioDAO;
import com.biblioteca.entity.Usuario;
import com.biblioteca.service.UsuarioService;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/biblioteca";
        String usuario = "postgres";
        String senha = "root21476601";

        try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
            if (conexao != null) {
                System.out.println("Banco de Dados conectado com sucesso!");

                UsuarioDAO usuarioDAO = new UsuarioDAO(conexao);
                UsuarioService usuarioService = new UsuarioService(usuarioDAO);

                Scanner scanner = new Scanner(System.in);
                System.out.println("Digite o nome do usuário:");
                String nome = scanner.nextLine();

                System.out.println("Digite o CPF do usuário:(Apenas números)");
                String cpf = scanner.nextLine();

                System.out.println("Digite o email do usuário:");
                String email = scanner.nextLine();

                System.out.println("Digite o id do endereço do usuário:");
                int idEndereco = Integer.parseInt(scanner.nextLine());

                Usuario novoUsuario = new Usuario(nome, cpf, email, idEndereco); 

                usuarioService.adicionarUsuario(novoUsuario);

                scanner.close();
            } else {
                System.out.println("Conexão com Banco de Dados falhou!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
