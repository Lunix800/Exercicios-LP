package com.example.projetolp3;

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
    public String nadar() {
        return("O pato " + cor  + " está nadando");
    }

    // Método para voar
    public String voar() {
        return("O pato que tem " + peso + "KG está voando");
    }

    // Método para grasnar
    public void grasnar() {
        System.out.println("O pato está grasnando...");
    }
}
