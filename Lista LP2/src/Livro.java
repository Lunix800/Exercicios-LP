public class Livro {
    // Atributos
    private String titulo;
    private String autor;
    private int paginas;

    // Construtor
    public Livro(String titulo, String autor, int paginas) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
    }

    // Método para abrir o livro
    public void abrir() {
        System.out.println("O livro foi aberto...");
    }

    // Método para folhear
    public void folhear() {
        System.out.println("Folheando o livro...");
    }

    // Método para ler o livro
    public void ler() {
        System.out.println("Lendo o livro...");
    }
}
