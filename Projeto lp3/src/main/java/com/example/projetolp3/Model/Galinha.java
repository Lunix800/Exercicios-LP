package com.example.projetolp3.Model;

public class Galinha {
    //Atributos
    private String raca;
    private String cor;
    private double pesogalinha;

    public Galinha(String raca, String cor, double pesogalinha) {
        this.raca = raca;
        this.cor = cor;
        this.pesogalinha = pesogalinha;
    }

    //Metodo cacarejar
    public String cacarejar() {
        return("A galinha " + cor + " está cacarejando...");
    }

    //Metodo peso
    public String peso() {
        return("A galinha da cor " + cor + " está com " + pesogalinha + " KG");

    }
}
