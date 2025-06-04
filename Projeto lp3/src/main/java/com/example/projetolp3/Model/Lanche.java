package com.example.projetolp3.Model;

public class Lanche {
    private Integer id; // Para o DAO
    private String nome;
    private double preco;
    private String tamanho; // Adicionado como atributo consistente com FXML

    // Construtor corrigido e principal para criar novos lanches
    public Lanche(String nome, double preco, String tamanho) {
        this.nome = nome;
        this.preco = preco;
        this.tamanho = tamanho;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public String getTamanho() { return tamanho; }
    public void setTamanho(String tamanho) { this.tamanho = tamanho; }


    // Método preco
    public String precoLanche() { // Renomeado para evitar conflito com o atributo "preco" se fosse um getter
        return "O lanche " + nome + " (" + tamanho + ") custa R$" + String.format("%.2f", preco).replace(",", ".");
    }

    // Método servir
    public String servir() {
        return "O lanche " + nome + " (" + tamanho + ") está pronto para ser servido.";
    }

    public String exibirdados() {
        return "Nome: " + nome + ", Preço: R$" + String.format("%.2f", preco).replace(",", ".") + ", Tamanho: " + tamanho;
    }

    @Override
    public String toString() {
        return nome + " - " + tamanho;
    }
}