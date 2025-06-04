package com.example.projetolp3.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQLite {
    public ConexaoSQLite() {
        conectar();
    }
    public static void conectar() {
        String url = "jdbc:sqlite:C:/Users/William/Documents/GitHub/Exercicios-LP/Projetolp3/bd/bd_projeto_classes.bd";

        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("✅ Conexão estabelecida com sucesso!");
        } catch (SQLException e) {
            System.out.println("❌ Erro ao conectar: " + e.getMessage());
        }
    }
}