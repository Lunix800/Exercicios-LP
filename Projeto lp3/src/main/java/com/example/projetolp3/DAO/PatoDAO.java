package com.example.projetolp3.DAO;

import com.example.projetolp3.Model.Pato;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatoDAO {
    // ATENÇÃO: Verifique se a URL do banco de dados está correta!
    private static final String URL = "jdbc:sqlite:C:/Users/William/Documents/GitHub/Exercicios-LP/Projetolp3/bd/bd_projeto_classes.bd";

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS pato (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "especie TEXT NOT NULL, " + // Corresponde a 'raca' no FXML
                "cor TEXT NOT NULL, " +
                "peso REAL NOT NULL)";    // REAL para double

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public boolean insert(Pato pato) throws SQLException {
        String sql = "INSERT INTO pato (especie, cor, peso) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pato.getEspecie());
            stmt.setString(2, pato.getCor());
            stmt.setDouble(3, pato.getPeso());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        pato.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public List<Pato> getAll() throws SQLException {
        List<Pato> patos = new ArrayList<>();
        String sql = "SELECT id, especie, cor, peso FROM pato";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pato pato = new Pato(
                        rs.getString("especie"),
                        rs.getString("cor"),
                        rs.getDouble("peso")
                );
                pato.setId(rs.getInt("id"));
                patos.add(pato);
            }
        }
        return patos;
    }

    public boolean update(Pato pato) throws SQLException {
        String sql = "UPDATE pato SET especie = ?, cor = ?, peso = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pato.getEspecie());
            stmt.setString(2, pato.getCor());
            stmt.setDouble(3, pato.getPeso());
            stmt.setInt(4, pato.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM pato WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}