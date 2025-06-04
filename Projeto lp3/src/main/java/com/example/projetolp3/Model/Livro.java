package com.example.projetolp3.Model;

public class Livro {
    private Integer id; // Adicionado para o DAO
    private String titulo;
    private String autor;
    private Integer paginas;

    public Livro(String titulo, String autor, Integer paginas) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public Integer getPaginas() { return paginas; }
    public void setPaginas(Integer paginas) { this.paginas = paginas; }

    // Método folhear
    public String folhear() {
        return "Folheando o livro \"" + titulo + "\" do autor " + autor + ", que tem " + paginas + " páginas.";
    }

    // Método ler
    public String ler() {
        return "Lendo o livro \"" + titulo + "\".";
    }

    // Método para exibir dados (útil para debug ou outras listagens)
    public String exibirdados() {
        return "Título: " + titulo + "\nAutor: " + autor + "\nPáginas: " + paginas;
    }

    @Override
    public String toString() {
        // Usado pelo ComboBox por padrão se não houver cell factory customizada.
        return titulo + " - " + autor;
    }
}