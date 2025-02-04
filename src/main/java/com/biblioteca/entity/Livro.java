package com.biblioteca.entity;

public class Livro {
    private int id;
    private String titulo;
    private String nomeAutor;
    private int anoPublicacao;
    private int quantidadeExemplares;
    private String genero;
    private boolean emprestado;

    public Livro() {
    }

    public Livro(int id, String titulo, String nomeAutor, int anoPublicacao, int quantidadeExemplares, String genero,
            boolean emprestado) {
        this.id = id;
        this.titulo = titulo;
        this.nomeAutor = nomeAutor;
        this.anoPublicacao = anoPublicacao;
        this.quantidadeExemplares = quantidadeExemplares;
        this.genero = genero;
        this.emprestado = emprestado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public int getQuantidadeExemplares() {
        return quantidadeExemplares;
    }

    public void setQuantidadeExemplares(int quantidadeExemplares) {
        this.quantidadeExemplares = quantidadeExemplares;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean isEmprestado() {
        return emprestado;
    }

    public void setEmprestado(boolean emprestado) {
        this.emprestado = emprestado;
    }
}
