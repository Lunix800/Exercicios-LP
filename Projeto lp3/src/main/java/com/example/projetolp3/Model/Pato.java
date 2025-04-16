package com.example.projetolp3.Model;

public class Pato {
    // Atributos
    private String especie;
    private String cor;
    private double peso;

    public Pato(String especie, String cor, double peso) {
        this.especie = especie;
        this.cor = cor;
        this.peso = peso;
    }

    //Metodo nadar
    public String nadar() {
        return("O pato " + cor  + " está nadando");
    }

    //Metodo voar
    public String voar() {
        return("O pato que tem " + peso + "KG está voando");
    }

}
