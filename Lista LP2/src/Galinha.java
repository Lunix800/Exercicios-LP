public class Galinha {
    // Atributos
    private String raca;
    private String cor;
    private double peso;

    // Construtor
    public Galinha(String raca, String cor, double peso) {
        this.raca = raca;
        this.cor = cor;
        this.peso = peso;
    }

    // Método para cacarejar
    public void cacarejar() {
        System.out.println("A galinha está cacarejando...");
    }

    // Método para botar um ovo
    public void botarOvo() {
        System.out.println("A galinha botou um ovo...");
    }

    // Método para ciscar
    public void ciscar() {
        System.out.println("A galinha está ciscando...");
    }
}
