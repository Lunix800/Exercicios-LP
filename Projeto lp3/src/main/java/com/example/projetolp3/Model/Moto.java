package com.example.projetolp3.Model;

public class Moto {
    //Atributos
    private String marca;
    private String modelo;
    private int cilindrada;

    public Moto(String marca, String modelo, int cilindrada) {
        this.marca = marca;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
    }

    //Metodo acelerar
    public String acelerar() {
        return("A moto de " + cilindrada + " cilindradas está acelerando");
    }

    //Metodo frear
    public String frear() {
        return("A moto " + modelo + " está freando");
    }

}
