package com.example.projetolp3;

public class Vaca {
    // Atributos
    private String raca;
    private String cor;
    private double peso;

    // Construtor
    public Vaca(String raca, String cor, double peso) {
        this.raca = raca;
        this.cor = cor;
        this.peso = peso;
    }

    // Método para mugir
    public String mugir() {
        return("A vaca da cor " + cor + " está mugindo");
    }

    // Método para comer
    public String comer() {
        return("A vaca que tem " + peso + "KG está comendo");
    }
}
