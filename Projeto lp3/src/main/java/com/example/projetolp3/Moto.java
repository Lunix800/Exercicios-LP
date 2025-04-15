package com.example.projetolp3;

public class Moto {
    // Atributos
    private String marca;
    private String modelo;
    private int cilindrada;

    // Construtor
    public Moto(String marca, String modelo, int cilindrada) {
        this.marca = marca;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
    }

    // Método para acelerar
    public String acelerar() {
        return("A moto de " + cilindrada + " cilindradas está acelerando");
    }

    // Método para frear
    public String frear() {
        return("A moto " + modelo + " está freando");
    }

    // Método para empinar
    public void empinar() {
        System.out.println("A moto está empinando...");
    }
}
