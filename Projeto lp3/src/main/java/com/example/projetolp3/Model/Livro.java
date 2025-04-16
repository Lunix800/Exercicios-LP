package com.example.projetolp3.Model;

public class Livro {
    //Atributos
    private String titulo;
    private String autor;
    private Integer paginas;

    public Livro(String titulo, String autor, Integer paginas) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
    }

    //Metodo folhear
    public String folhear() {
        return("Folheando o livro do autor " + autor + " que tem " + paginas + " paginas");
    }

    //Metodo ler
    public String ler() {
        return("Lendo o livro " + titulo);
    }
}
