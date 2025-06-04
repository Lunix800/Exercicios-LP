package com.example.projetolp3.Model;

public class Moto {
    private Integer id; // Adicionado para o DAO
    private String marca;
    private String modelo;
    private int cilindrada;

    public Moto(String marca, String modelo, int cilindrada) {
        this.marca = marca;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public int getCilindrada() { return cilindrada; }
    public void setCilindrada(int cilindrada) { this.cilindrada = cilindrada; }

    // Método acelerar
    public String acelerar() {
        return "A moto " + marca + " " + modelo + " de " + cilindrada + "cc está acelerando: Vrummm!";
    }

    // Método frear
    public String frear() {
        return "A moto " + marca + " " + modelo + " está freando.";
    }

    // Método para exibir dados
    public String exibirdados() {
        return "Marca: " + marca + "\nModelo: " + modelo + "\nCilindrada: " + cilindrada + "cc";
    }

    @Override
    public String toString() {
        // Usado pelo ComboBox por padrão
        return marca + " " + modelo;
    }
}