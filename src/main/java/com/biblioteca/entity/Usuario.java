package com.biblioteca.entity;

public class Usuario {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private int enderecoId; // Chave estrangeira para a tabela endereco

    public Usuario() {
    }

    public Usuario(String nome, String cpf, String email, int enderecoId) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.enderecoId = enderecoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(int enderecoId) {
        this.enderecoId = enderecoId;
    }
}
