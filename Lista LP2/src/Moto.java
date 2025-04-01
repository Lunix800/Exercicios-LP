public class Moto {
    // Atributos
    private String marca;
    private String modelo;
    private int cilindrada;

    // Construtor
    public Moto(String marca, String modelo, int cilindrada) {
        this.marca = marca;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
    }

    // Método para acelerar
    public void acelerar() {
        System.out.println("A moto está acelerando...");
    }

    // Método para frear
    public void frear() {
        System.out.println("A moto está freando...");
    }

    // Método para empinar
    public void empinar() {
        System.out.println("A moto está empinando...");
    }
}
