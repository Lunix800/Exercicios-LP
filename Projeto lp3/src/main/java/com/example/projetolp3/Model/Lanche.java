package com.example.projetolp3.Model;

public class Lanche {
    // Atributos
    private String nome;
    private double preco;
    private String tamanho;

    public Lanche(String nome, String ingredientes, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    //Metodo preco
    public String preco() {
        return("O lanche " + nome + " custa R$" + preco);
    }

    //Metodo servir
    public String servir() {
        return("O lanche " + nome + " está pronto para ser servido.");
    }
}


