package com.example.projetolp3.Model;

public class Celular {
    private Integer id;
    private String marcacelular;
    private String modelo;
    private Integer capacidadeBateria;

    public Celular(String marcacelular, String modelo, Integer capacidadeBateria) {
        this.marcacelular = marcacelular;
        this.modelo = modelo;
        this.capacidadeBateria = capacidadeBateria;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getMarcacelular() { return marcacelular; }
    public void setMarcacelular(String marcacelular) { this.marcacelular = marcacelular; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public Integer getCapacidadeBateria() { return capacidadeBateria; }
    public void setCapacidadeBateria(Integer capacidadeBateria) { this.capacidadeBateria = capacidadeBateria; }

    // Métodos específicos
    public String bateria() {
        return("O celular está com " + capacidadeBateria + "% de carga");
    }

    public String marca() {
        return("A marca do celular é " + marcacelular);
    }

    public String exibirdados() {
        return "Marca: " + marcacelular + "\nModelo: " + modelo + "\nBateria: " + capacidadeBateria + "%";
    }

    @Override
    public String toString() {
        // Usado pelo ComboBox se não houver cell factory, mas personalizamos no controller
        return marcacelular + " " + modelo;
    }
}