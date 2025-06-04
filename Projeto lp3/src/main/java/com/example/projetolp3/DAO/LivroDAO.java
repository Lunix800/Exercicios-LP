package com.example.projetolp3.DAO;

import com.example.projetolp3.Model.Livro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {
    // ATENÇÃO: Verifique se a URL do banco de dados está correta!
    private static final String URL = "jdbc:sqlite:C:/Users/William/Documents/GitHub/Exercicios-LP/Projetolp3/bd/bd_projeto_classes.bd";

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS livro (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                "autor TEXT NOT NULL, " +
                "paginas INTEGER NOT NULL)";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public boolean insert(Livro livro) throws SQLException {
        String sql = "INSERT INTO livro (titulo, autor, paginas) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getPaginas());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        livro.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public List<Livro> getAll() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, paginas FROM livro";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("paginas")
                );
                livro.setId(rs.getInt("id"));
                livros.add(livro);
            }
        }
        return livros;
    }

    public boolean update(Livro livro) throws SQLException {
        String sql = "UPDATE livro SET titulo = ?, autor = ?, paginas = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getPaginas());
            stmt.setInt(4, livro.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM livro WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}