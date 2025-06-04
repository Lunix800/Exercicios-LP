package com.example.projetolp3.Model;

public class Vaca {
    private Integer id; // Adicionado para o DAO
    private String raca;
    private String cor;
    private double peso;

    public Vaca(String raca, String cor, double peso) {
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

    // Método mugir
    public String mugir() {
        return "A vaca " + this.raca + " de cor " + this.cor + " está mugindo: Muuuu!";
    }

    // Método comer
    public String comer() {
        return "A vaca " + this.raca + " que pesa " + String.format("%.2f", this.peso).replace(",",".") + "kg está comendo capim.";
    }

    // Método para exibir dados
    public String exibirdados() {
        return "Raça: " + raca + "\nCor: " + cor + "\nPeso: " + String.format("%.2f", peso).replace(",",".") + "kg";
    }

    @Override
    public String toString() {
        // Usado pelo ComboBox por padrão
        return raca + " - " + cor;
    }
}