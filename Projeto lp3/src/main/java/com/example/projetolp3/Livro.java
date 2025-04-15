package com.example.projetolp3;

public class Livro {
    // Atributos
    private String titulo;
    private String autor;
    private Integer paginas;

    // Construtor
    public Livro(String titulo, String autor, Integer paginas) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
    }

    // Método para abrir o livro
    public String abrir() {
        return("O livro foi aberto...");
    }

    // Método para folhear
    public String folhear() {
        return("Folheando o livro do autor " + autor + " que tem " + paginas + " paginas");
    }

    // Método para ler o livro
    public String ler() {
        return("Lendo o livro " + titulo);
    }
}
