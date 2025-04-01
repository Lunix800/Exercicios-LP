public class Carro {
    // Atributos
    private String marca;
    private String modelo;
    private int ano;

    // Construtor
    public Carro(String marca, String modelo, int ano) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
    }

    // Método para acelerar
    public void acelerar() {
        System.out.println("O carro está acelerando...");
    }

    // Método para frear
    public void frear() {
        System.out.println("O carro está freando...");
    }

    // Método para ligar o carro
    public void ligar() {
        System.out.println("O carro foi ligado...");
    }
}
