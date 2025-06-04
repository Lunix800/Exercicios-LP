package com.example.projetolp3.Model;

public class Pato {
    private Integer id; // Adicionado para o DAO
    private String especie; // No FXML, o campo é txtRacaPato
    private String cor;
    private double peso;

    // Construtor alinhado com os atributos da classe
    public Pato(String especie, String cor, double peso) {
        this.especie = especie;
        this.cor = cor;
        this.peso = peso;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    // Método nadar
    public String nadar() {
        return "O pato " + this.especie + " de cor " + this.cor + " está nadando.";
    }

    // Método voar
    public String voar() {
        return "O pato " + this.especie + " que pesa " + String.format("%.2f", this.peso).replace(",",".") + "kg está voando.";
    }

    // Método para exibir dados
    public String exibirdados() {
        return "Espécie/Raça: " + especie + "\nCor: " + cor + "\nPeso: " + String.format("%.2f", peso).replace(",",".") + "kg";
    }

    @Override
    public String toString() {
        // Usado pelo ComboBox por padrão
        return especie + " - " + cor;
    }
}