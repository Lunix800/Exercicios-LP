package com.example.projetolp3.DAO;

import com.example.projetolp3.Model.Moto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotoDAO {
    // ATENÇÃO: Verifique se a URL do banco de dados está correta!
    private static final String URL = "jdbc:sqlite:C:/Users/William/Documents/GitHub/Exercicios-LP/Projetolp3/bd/bd_projeto_classes.bd";

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS moto (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "marca TEXT NOT NULL, " +
                "modelo TEXT NOT NULL, " +
                "cilindrada INTEGER NOT NULL)";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public boolean insert(Moto moto) throws SQLException {
        String sql = "INSERT INTO moto (marca, modelo, cilindrada) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, moto.getMarca());
            stmt.setString(2, moto.getModelo());
            stmt.setInt(3, moto.getCilindrada());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        moto.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public List<Moto> getAll() throws SQLException {
        List<Moto> motos = new ArrayList<>();
        String sql = "SELECT id, marca, modelo, cilindrada FROM moto";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Moto moto = new Moto(
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("cilindrada")
                );
                moto.setId(rs.getInt("id"));
                motos.add(moto);
            }
        }
        return motos;
    }

    public boolean update(Moto moto) throws SQLException {
        String sql = "UPDATE moto SET marca = ?, modelo = ?, cilindrada = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, moto.getMarca());
            stmt.setString(2, moto.getModelo());
            stmt.setInt(3, moto.getCilindrada());
            stmt.setInt(4, moto.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM moto WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}