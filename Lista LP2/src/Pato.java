public class Pato {
    // Atributos
    private String especie;
    private String cor;
    private double peso;

    // Construtor
    public Pato(String especie, String cor, double peso) {
        this.especie = especie;
        this.cor = cor;
        this.peso = peso;
    }

    // Método para nadar
    public void nadar() {
        System.out.println("O pato está nadando...");
    }

    // Método para voar
    public void voar() {
        System.out.println("O pato está voando...");
    }

    // Método para grasnar
    public void grasnar() {
        System.out.println("O pato está grasnando...");
    }
}
