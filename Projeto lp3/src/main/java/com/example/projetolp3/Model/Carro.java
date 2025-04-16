package com.example.projetolp3.Model;

public class Carro {
    //Atributos
    private String marca;
    private String modelo;
    private Integer ano;

    public Carro(String marca, String modelo, Integer ano) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
    }
    //Metodo marca
    public String marca() {
        return("O carro é da marca: " + marca + " e modelo: " + modelo);

    }
    //Metodo Ano
    public String ano() {
        return("O carro é do ano: " + ano);
    }
}
