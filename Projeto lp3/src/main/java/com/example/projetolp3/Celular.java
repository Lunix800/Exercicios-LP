package com.example.projetolp3;

public class Celular {
    // Atributos
    private String marcacelular;
    private String modelo;
    private Integer capacidadeBateria;

    // Construtor
    public Celular(String marcacelular, String modelo, Integer capacidadeBateria) {
        this.marcacelular = marcacelular;
        this.modelo = modelo;
        this.capacidadeBateria = capacidadeBateria;
    }

    // Método para verificar a bateria do celular
    public String bateria() {
        return("O celular está com " + capacidadeBateria + "% de carga");
    }

    // Método para verificar a marca do celular
    public String marca() {
        return("A marca do celular é " + marcacelular);
    }

    // Método para carregar a bateria
    public void carregarBateria() {
        System.out.println("O celular está carregando...");
    }
}
