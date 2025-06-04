package com.example.projetolp3.DAO;

import com.example.projetolp3.Model.Cachorro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CachorroDAO {
    private static final String URL = "jdbc:sqlite:C:/Users/William/Documents/GitHub/Exercicios-LP/Projetolp3/bd/bd_projeto_classes.bd";

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS cachorro (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "raca TEXT NOT NULL, " +
                "idade INTEGER NOT NULL)";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public boolean insert(Cachorro cachorro) throws SQLException {
        String sql = "INSERT INTO cachorro (nome, raca, idade) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cachorro.getNome());
            stmt.setString(2, cachorro.getRaca());
            stmt.setInt(3, cachorro.getIdade());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        cachorro.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public List<Cachorro> getAll() throws SQLException {
        List<Cachorro> cachorros = new ArrayList<>();
        String sql = "SELECT id, nome, raca, idade FROM cachorro";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cachorro cachorro = new Cachorro(
                        rs.getString("nome"),
                        rs.getString("raca"),
                        rs.getInt("idade")
                );
                cachorro.setId(rs.getInt("id"));
                cachorros.add(cachorro);
            }
        }
        return cachorros;
    }

    public boolean update(Cachorro cachorro) throws SQLException {
        String sql = "UPDATE cachorro SET nome = ?, raca = ?, idade = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cachorro.getNome());
            stmt.setString(2, cachorro.getRaca());
            stmt.setInt(3, cachorro.getIdade());
            stmt.setInt(4, cachorro.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM cachorro WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}