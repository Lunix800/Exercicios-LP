package com.example.projetolp3.DAO;

import com.example.projetolp3.Model.Carro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO {
    private static final String URL = "jdbc:sqlite:C:/Users/William/Documents/GitHub/Exercicios-LP/Projetolp3/bd/bd_projeto_classes.bd";

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS carro (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "marca TEXT NOT NULL, " +
                "modelo TEXT NOT NULL, " +
                "ano INTEGER NOT NULL)";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public boolean insert(Carro carro) throws SQLException {
        String sql = "INSERT INTO carro (marca, modelo, ano) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, carro.getMarca());
            stmt.setString(2, carro.getModelo());
            stmt.setInt(3, carro.getAno());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        carro.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public boolean update(Carro carro) throws SQLException {
        String sql = "UPDATE carro SET marca = ?, modelo = ?, ano = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, carro.getMarca());
            stmt.setString(2, carro.getModelo());
            stmt.setInt(3, carro.getAno());
            stmt.setInt(4, carro.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public List<Carro> getAll() throws SQLException {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, marca, modelo, ano FROM carro";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano")
                );
                carro.setId(rs.getInt("id"));
                carros.add(carro);
            }
        }
        return carros;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM carro WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}