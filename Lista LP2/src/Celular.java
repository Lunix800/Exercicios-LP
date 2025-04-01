public class Celular {
    // Atributos
    private String marca;
    private String modelo;
    private int capacidadeBateria;

    // Construtor
    public Celular(String marca, String modelo, int capacidadeBateria) {
        this.marca = marca;
        this.modelo = modelo;
        this.capacidadeBateria = capacidadeBateria;
    }

    // Método para ligar o celular
    public void ligar() {
        System.out.println("O celular está ligado...");
    }

    // Método para tirar uma foto
    public void tirarFoto() {
        System.out.println("O celular tirou uma foto...");
    }

    // Método para carregar a bateria
    public void carregarBateria() {
        System.out.println("O celular está carregando...");
    }
}
