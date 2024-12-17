package model;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    public List<Usuario> usuarios = new ArrayList<>(); // lista de usuarios
    public List<Livro> livros = new ArrayList<>(); // lista de livros
    public List<Emprestimo> emprestimos = new ArrayList<>(); // lista de emprestimos

    public Biblioteca() {
    }

    public Biblioteca(List<Usuario> usuarios, List<Livro> livros, List<Emprestimo> emprestimos) {
        this.usuarios = usuarios;
        this.livros = livros;
        this.emprestimos = emprestimos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

}
