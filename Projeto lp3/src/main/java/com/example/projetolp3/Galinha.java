package com.example.projetolp3;

public class Galinha {
    // Atributos
    private String raca;
    private String cor;
    private double pesogalinha;

    // Construtor
    public Galinha(String raca, String cor, double pesogalinha) {
        this.raca = raca;
        this.cor = cor;
        this.pesogalinha = pesogalinha;
    }

    // Método para cacarejar
    public String cacarejar() {
        return("A galinha " + cor + " está cacarejando...");
    }

    // Método para botar um ovo
    public void botarOvo() {
        System.out.println("A galinha botou um ovo...");
    }

    // Método para ciscar
    public String peso() {
        return("A galinha da cor " + cor + " está com " + pesogalinha + " KG");

    }
}
