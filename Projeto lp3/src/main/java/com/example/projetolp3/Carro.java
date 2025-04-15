package com.example.projetolp3;

public class Carro {
    // Atributos
    private String marca;
    private String modelo;
    private Integer ano;

    // Construtor
    public Carro(String marca, String modelo, Integer ano) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
    }

    // Método marca
    public String marca() {
        return("O carro é da marca: " + marca + " e modelo: " + modelo);

    }

    // Método para frear
    public void modelo() {
        System.out.println("O carro está freando...");
    }

    // Método para ligar o carro
    public String ano() {
        return("O carro é do ano: " + ano);
    }
}
