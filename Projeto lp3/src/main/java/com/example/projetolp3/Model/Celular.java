package com.example.projetolp3.Model;

public class Celular {
    //Atributos
    private String marcacelular;
    private String modelo;
    private Integer capacidadeBateria;

    public Celular(String marcacelular, String modelo, Integer capacidadeBateria) {
        this.marcacelular = marcacelular;
        this.modelo = modelo;
        this.capacidadeBateria = capacidadeBateria;
    }

    //Metodo bateria
    public String bateria() {
        return("O celular está com " + capacidadeBateria + "% de carga");
    }

    //Metodo marca
    public String marca() {
        return("A marca do celular é " + marcacelular);
    }
}
