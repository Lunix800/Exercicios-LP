package com.example.projetolp3;

public class Cachorro {
    // Atributos da classe Cachorro
    private String nome;    // Nome do cachorro, como "Rex", "Bolinha"
    private String raca;    // Raça do cachorro, como "Labrador", "Poodle"
    private Integer idade;      // Idade do cachorro em anos

    // Construtor da classe Cachorro
    public Cachorro (String nome, String raca, Integer idade) {
        this.nome = nome;     // Inicializa o nome com o valor passado
        this.raca = raca;     // Inicializa a raça com o valor passado
        this.idade = idade;   // Inicializa a idade com o valor passado
    }

    // Ação de dar nome
    public String nome() {
        return("O nome do cachorro é " + this.nome + " e ele tem " + this.idade + " anos");

    }

    // Ação de latir
    public String latir() {
        return("O cachorro " + this.nome + " está latindo");
    }

    // Ação de comer
    public void comer() {
        System.out.println("O cachorro está comendo...");
    }
}
