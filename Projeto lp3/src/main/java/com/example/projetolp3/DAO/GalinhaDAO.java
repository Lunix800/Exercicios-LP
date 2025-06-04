package com.example.projetolp3.DAO;

import com.example.projetolp3.Model.Galinha;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GalinhaDAO {
    // ATENÇÃO: Verifique se a URL do banco de dados está correta!
    private static final String URL = "jdbc:sqlite:C:/Users/William/Documents/GitHub/Exercicios-LP/Projetolp3/bd/bd_projeto_classes.bd";

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS galinha (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "raca TEXT NOT NULL, " +
                "cor TEXT NOT NULL, " +
                "pesogalinha REAL NOT NULL)"; // REAL para double em SQLite

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public boolean insert(Galinha galinha) throws SQLException {
        String sql = "INSERT INTO galinha (raca, cor, pesogalinha) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, galinha.getRaca());
            stmt.setString(2, galinha.getCor());
            stmt.setDouble(3, galinha.getPesogalinha());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        galinha.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public List<Galinha> getAll() throws SQLException {
        List<Galinha> galinhas = new ArrayList<>();
        String sql = "SELECT id, raca, cor, pesogalinha FROM galinha";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Galinha galinha = new Galinha(
                        rs.getString("raca"),
                        rs.getString("cor"),
                        rs.getDouble("pesogalinha")
                );
                galinha.setId(rs.getInt("id"));
                galinhas.add(galinha);
            }
        }
        return galinhas;
    }

    public boolean update(Galinha galinha) throws SQLException {
        String sql = "UPDATE galinha SET raca = ?, cor = ?, pesogalinha = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, galinha.getRaca());
            stmt.setString(2, galinha.getCor());
            stmt.setDouble(3, galinha.getPesogalinha());
            stmt.setInt(4, galinha.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM galinha WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}