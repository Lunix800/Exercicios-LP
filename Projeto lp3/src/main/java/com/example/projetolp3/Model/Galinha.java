package com.example.projetolp3.Model;

public class Galinha {
    private Integer id;
    private String raca;
    private String cor;
    private double pesogalinha;

    public Galinha(String raca, String cor, double pesogalinha) {
        this.raca = raca;
        this.cor = cor;
        this.pesogalinha = pesogalinha;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    public double getPesogalinha() { return pesogalinha; }
    public void setPesogalinha(double pesogalinha) { this.pesogalinha = pesogalinha; }

    // Métodos específicos
    public String cacarejar() {
        return "A galinha " + this.cor + " está cacarejando: Cocoricó!";
    }

    public String peso() {
        return "A galinha da cor " + this.cor + " está com " + String.format("%.2f", pesogalinha).replace(",", ".") + " KG";
    }

    public String exibirdados() {
        return "Raça: " + raca + ", Cor: " + cor + ", Peso: " + String.format("%.2f", pesogalinha).replace(",", ".") + "kg";
    }

    @Override
    public String toString() {
        // Usado pelo ComboBox por padrão se não houver cell factory customizada.
        return raca + " - " + cor;
    }
}