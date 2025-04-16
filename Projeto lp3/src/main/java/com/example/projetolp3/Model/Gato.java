package com.example.projetolp3.Model;

public class Gato {
    // Atributos
    private String raca;
    private String cor;
    private double peso;

    public Gato(String raca, String cor, double peso) {
        this.raca = raca;
        this.cor = cor;
        this.peso = peso;
    }

    //Metodo miar
    public String miar() {
        return("O gato da cor " + cor + " está miando");
    }

    //Metodo arranhar
    public String arranhar() {
        return("O gato da raça " + raca + " está arranhando o sofá");
    }

}
