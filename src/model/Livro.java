package model;

public class Livro {
    public String titulo;
    public String nomeAutor;
    public int anoPublicacao;
    public int quantidadeExemplares;
    public String genero;
    public boolean emprestado = false;

    public Livro(String titulo, String nomeAutor, int anoPublicacao, int quantidadeExemplares, String genero) {
        this.titulo = titulo;
        this.nomeAutor = nomeAutor;
        this.anoPublicacao = anoPublicacao;
        this.quantidadeExemplares = quantidadeExemplares;
        this.genero = genero;
    }

    public String getTitulo() { //pegar
        return titulo;
    }

    public void setTitulo(String titulo) { //definir
        this.titulo = titulo;
    }

    public String getNome_autor() {
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
}
