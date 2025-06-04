package com.example.projetolp3.DAO;

import com.example.projetolp3.Model.Vaca;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacaDAO {
    // ATENÇÃO: Verifique se a URL do banco de dados está correta!
    private static final String URL = "jdbc:sqlite:C:/Users/William/Documents/GitHub/Exercicios-LP/Projetolp3/bd/bd_projeto_classes.bd";

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS vaca (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "raca TEXT NOT NULL, " +
                "cor TEXT NOT NULL, " +
                "peso REAL NOT NULL)";    // REAL para double

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public boolean insert(Vaca vaca) throws SQLException {
        String sql = "INSERT INTO vaca (raca, cor, peso) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, vaca.getRaca());
            stmt.setString(2, vaca.getCor());
            stmt.setDouble(3, vaca.getPeso());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        vaca.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public List<Vaca> getAll() throws SQLException {
        List<Vaca> vacas = new ArrayList<>();
        String sql = "SELECT id, raca, cor, peso FROM vaca";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vaca vaca = new Vaca(
                        rs.getString("raca"),
                        rs.getString("cor"),
                        rs.getDouble("peso")
                );
                vaca.setId(rs.getInt("id"));
                vacas.add(vaca);
            }
        }
        return vacas;
    }

    public boolean update(Vaca vaca) throws SQLException {
        String sql = "UPDATE vaca SET raca = ?, cor = ?, peso = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vaca.getRaca());
            stmt.setString(2, vaca.getCor());
            stmt.setDouble(3, vaca.getPeso());
            stmt.setInt(4, vaca.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM vaca WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}