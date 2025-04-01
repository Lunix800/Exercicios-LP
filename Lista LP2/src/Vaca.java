public class Vaca {
    // Atributos
    private String raca;
    private String cor;
    private double peso;

    // Construtor
    public Vaca(String raca, String cor, double peso) {
        this.raca = raca;
        this.cor = cor;
        this.peso = peso;
    }

    // Método para mugir
    public void mugir() {
        System.out.println("A vaca está mugindo...");
    }

    // Método para produzir leite
    public void produzirLeite() {
        System.out.println("A vaca está produzindo leite...");
    }

    // Método para comer
    public void comer() {
        System.out.println("A vaca está comendo...");
    }
}
