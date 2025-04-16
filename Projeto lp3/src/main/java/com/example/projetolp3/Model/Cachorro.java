package com.example.projetolp3.Model;

public class Cachorro {
    //Atributos
    private String nome;
    private String raca;
    private Integer idade;

    public Cachorro (String nome, String raca, Integer idade) {
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
    }
    //Metodo Nome
    public String nome() {
        return("O nome do cachorro é " + this.nome + " e ele tem " + this.idade + " anos");
    }
    //Metodo Latir
    public String latir() {
        return("O cachorro " + this.nome + " está latindo");
    }

}
