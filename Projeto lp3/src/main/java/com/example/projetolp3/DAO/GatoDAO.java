package com.example.projetolp3.DAO;

import com.example.projetolp3.Model.Gato;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GatoDAO {
    // ATENÇÃO: Verifique se a URL do banco de dados está correta!
    private static final String URL = "jdbc:sqlite:C:/Users/William/Documents/GitHub/Exercicios-LP/Projetolp3/bd/bd_projeto_classes.bd";

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS gato (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "raca TEXT NOT NULL, " +
                "cor TEXT NOT NULL, " +
                "peso REAL NOT NULL)"; // REAL para double em SQLite

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public boolean insert(Gato gato) throws SQLException {
        String sql = "INSERT INTO gato (raca, cor, peso) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, gato.getRaca());
            stmt.setString(2, gato.getCor());
            stmt.setDouble(3, gato.getPeso());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        gato.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public List<Gato> getAll() throws SQLException {
        List<Gato> gatos = new ArrayList<>();
        String sql = "SELECT id, raca, cor, peso FROM gato";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Gato gato = new Gato(
                        rs.getString("raca"),
                        rs.getString("cor"),
                        rs.getDouble("peso")
                );
                gato.setId(rs.getInt("id"));
                gatos.add(gato);
            }
        }
        return gatos;
    }

    public boolean update(Gato gato) throws SQLException {
        String sql = "UPDATE gato SET raca = ?, cor = ?, peso = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, gato.getRaca());
            stmt.setString(2, gato.getCor());
            stmt.setDouble(3, gato.getPeso());
            stmt.setInt(4, gato.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM gato WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}