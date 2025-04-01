public class Gato {
    // Atributos
    private String raca;
    private String cor;
    private double peso;

    // Construtor
    public Gato(String raca, String cor, double peso) {
        this.raca = raca;
        this.cor = cor;
        this.peso = peso;
    }

    // Método para miar
    public void miar() {
        System.out.println("O gato está miando...");
    }

    // Método para arranhar
    public void arranhar() {
        System.out.println("O gato está arranhando...");
    }

    // Método para pular
    public void pular() {
        System.out.println("O gato pulou para um lugar alto...");
    }
}
