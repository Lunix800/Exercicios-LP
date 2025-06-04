package com.example.projetolp3.DAO;

import com.example.projetolp3.Model.Celular;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CelularDAO {
    private static final String URL = "jdbc:sqlite:C:/Users/William/Documents/GitHub/Exercicios-LP/Projetolp3/bd/bd_projeto_classes.bd"; // Verifique este caminho

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS celular (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "marcacelular TEXT NOT NULL, " +
                "modelo TEXT NOT NULL, " +
                "capacidadeBateria INTEGER NOT NULL)";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public boolean insert(Celular celular) throws SQLException {
        String sql = "INSERT INTO celular (marcacelular, modelo, capacidadeBateria) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, celular.getMarcacelular());
            stmt.setString(2, celular.getModelo());
            stmt.setInt(3, celular.getCapacidadeBateria());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        celular.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public List<Celular> getAll() throws SQLException {
        List<Celular> celulares = new ArrayList<>();
        String sql = "SELECT id, marcacelular, modelo, capacidadeBateria FROM celular";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Celular celular = new Celular(
                        rs.getString("marcacelular"),
                        rs.getString("modelo"),
                        rs.getInt("capacidadeBateria")
                );
                celular.setId(rs.getInt("id"));
                celulares.add(celular);
            }
        }
        return celulares;
    }

    public boolean update(Celular celular) throws SQLException {
        String sql = "UPDATE celular SET marcacelular = ?, modelo = ?, capacidadeBateria = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, celular.getMarcacelular());
            stmt.setString(2, celular.getModelo());
            stmt.setInt(3, celular.getCapacidadeBateria());
            stmt.setInt(4, celular.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM celular WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}