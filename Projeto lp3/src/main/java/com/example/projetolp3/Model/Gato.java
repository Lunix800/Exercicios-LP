package com.example.projetolp3.Model;

public class Gato {
    private Integer id; // Adicionado
    private String raca;
    private String cor;
    private double peso; // Mantido como double

    public Gato(String raca, String cor, double peso) {
        this.raca = raca;
        this.cor = cor;
        this.peso = peso;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    // Métodos específicos
    public String miar() {
        return "O gato da cor " + this.cor + " está miando: Miau!";
    }

    public String arranhar() {
        return "O gato da raça " + this.raca + " está arranhando o sofá!";
    }

    public String exibirdados() {
        return "Raça: " + raca + ", Cor: " + cor + ", Peso: " + String.format("%.2f", peso).replace(",", ".") + "kg";
    }

    @Override
    public String toString() {
        return raca + " - " + cor;
    }
}