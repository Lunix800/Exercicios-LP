package com.example.projetolp3.Model;

public class Cachorro {
    private Integer id;
    private String nome;
    private String raca;
    private Integer idade;

    public Cachorro(String nome, String raca, Integer idade) {
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }
    public Integer getIdade() { return idade; }
    public void setIdade(Integer idade) { this.idade = idade; }

    // Métodos específicos
    public String nome() {
        return "Nome: " + this.nome;
    }

    public String latir() {
        return this.nome + " está latindo: Au Au!";
    }

    public String exibirdados() {
        return "Nome: " + nome + "\nRaça: " + raca + "\nIdade: " + idade + " anos";
    }
}