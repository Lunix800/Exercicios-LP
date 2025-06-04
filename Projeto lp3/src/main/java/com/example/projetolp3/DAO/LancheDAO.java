package com.example.projetolp3.DAO;

import com.example.projetolp3.Model.Lanche;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LancheDAO {
    // ATENÇÃO: Verifique se a URL do banco de dados está correta!
    private static final String URL = "jdbc:sqlite:C:/Users/William/Documents/GitHub/Exercicios-LP/Projetolp3/bd/bd_projeto_classes.bd";

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS lanche (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "preco REAL NOT NULL, " +
                "tamanho TEXT NOT NULL)";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public boolean insert(Lanche lanche) throws SQLException {
        String sql = "INSERT INTO lanche (nome, preco, tamanho) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, lanche.getNome());
            stmt.setDouble(2, lanche.getPreco());
            stmt.setString(3, lanche.getTamanho());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        lanche.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public List<Lanche> getAll() throws SQLException {
        List<Lanche> lanches = new ArrayList<>();
        String sql = "SELECT id, nome, preco, tamanho FROM lanche";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Lanche lanche = new Lanche(
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getString("tamanho")
                );
                lanche.setId(rs.getInt("id"));
                lanches.add(lanche);
            }
        }
        return lanches;
    }

    public boolean update(Lanche lanche) throws SQLException {
        String sql = "UPDATE lanche SET nome = ?, preco = ?, tamanho = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lanche.getNome());
            stmt.setDouble(2, lanche.getPreco());
            stmt.setString(3, lanche.getTamanho());
            stmt.setInt(4, lanche.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM lanche WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}