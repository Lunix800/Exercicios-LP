package com.example.projetolp3;

public class Gato {
    // Atributos
    private String raca;
    private String cor;
    private double peso;

    // Construtor
    public Gato(String raca, String cor, double peso) {
        this.raca = raca;
        this.cor = cor;
        this.peso = peso;
    }

    // Método para miar
    public String miar() {
        return("O gato da cor " + cor + " está miando");
    }

    // Método para arranhar
    public String arranhar() {
        return("O gato da raça " + raca + " está arranhando o sofá");
    }

    // Método para pular
    public void pular() {
        System.out.println("O gato pulou para um lugar alto...");
    }
}
