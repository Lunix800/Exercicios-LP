package com.example.projetolp3;

public class Lanche {
    // Atributos
    private String nome;
    private double preco;

    // Construtor
    public Lanche(String nome, String ingredientes, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    // Método para preparar o lanche
    public String preco() {
        return("O lanche " + nome + " custa R$" + preco);
    }

    // Método para servir o lanche se estiver pronto
    public String servir() {
        return("O lanche " + nome + " está pronto para ser servido.");
    }
}


