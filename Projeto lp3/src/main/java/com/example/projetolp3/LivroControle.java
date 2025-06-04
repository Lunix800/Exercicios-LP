package com.example.projetolp3;

import com.example.projetolp3.Model.Livro;
import com.example.projetolp3.DAO.LivroDAO; // Importar o DAO
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LivroControle {

    @FXML private TextField txtTituloLivro;
    @FXML private TextField txtAutorLivro;
    @FXML private TextField txtPaginasLivro;
    @FXML private TextArea txtResultado;
    @FXML private ComboBox<Livro> cbLivros; // Adicionado
    @FXML private Label errorLabel;
    @FXML private Button btnSalvar;         // Adicionado
    @FXML private Button btnDeletarLivro;   // Adicionado
    @FXML private Button btnFolhearLivro;   // Mantido
    @FXML private Button btnLerLivro;       // Mantido

    private LivroDAO livroDAO;
    private Livro livroSelecionado;

    @FXML
    public void initialize() {
        try {
            livroDAO = new LivroDAO();
            livroDAO.createTable();
            carregarLivros();
            atualizarInterface();
        } catch (SQLException e) {
            mostrarErro("Erro ao conectar com o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void carregarLivros() {
        try {
            ObservableList<Livro> livros = FXCollections.observableArrayList(livroDAO.getAll());
            cbLivros.setItems(livros);

            cbLivros.setCellFactory(param -> new ListCell<Livro>() {
                @Override
                protected void updateItem(Livro livro, boolean empty) {
                    super.updateItem(livro, empty);
                    if (empty || livro == null) {
                        setText(null);
                    } else {
                        setText(livro.getTitulo() + " - " + livro.getAutor() + " (" + livro.getPaginas() + " pág.)");
                    }
                }
            });

            cbLivros.setButtonCell(new ListCell<Livro>() {
                @Override
                protected void updateItem(Livro livro, boolean empty) {
                    super.updateItem(livro, empty);
                    if (empty || livro == null) {
                        setText("Selecione um livro");
                    } else {
                        setText(livro.getTitulo() + " - " + livro.getAutor());
                    }
                }
            });

            cbLivros.getSelectionModel().selectedItemProperty().addListener(
                    (obs, oldVal, newVal) -> {
                        livroSelecionado = newVal;
                        atualizarInterface();
                    });
        } catch (SQLException e) {
            mostrarErro("Erro ao carregar livros: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void atualizarInterface() {
        if (livroSelecionado != null) {
            txtTituloLivro.setText(livroSelecionado.getTitulo());
            txtAutorLivro.setText(livroSelecionado.getAutor());
            txtPaginasLivro.setText(String.valueOf(livroSelecionado.getPaginas()));
            btnSalvar.setText("Atualizar");
            txtResultado.setText("Editando: " + livroSelecionado.getTitulo());
        } else {
            limparCampos();
            btnSalvar.setText("Salvar");
            txtResultado.setText("Preencha os dados para cadastrar um novo livro ou selecione um para editar.");
        }
    }

    @FXML
    private void salvar(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }
        try {
            int paginas = Integer.parseInt(txtPaginasLivro.getText());
            String titulo = txtTituloLivro.getText();
            String autor = txtAutorLivro.getText();

            if (livroSelecionado == null) { // Criar novo
                Livro novoLivro = new Livro(titulo, autor, paginas);
                if (livroDAO.insert(novoLivro)) {
                    mostrarMensagem("Livro cadastrado com sucesso!");
                    carregarLivros();
                    limparCampos();
                } else {
                    mostrarErro("Falha ao cadastrar livro.");
                }
            } else { // Atualizar existente
                livroSelecionado.setTitulo(titulo);
                livroSelecionado.setAutor(autor);
                livroSelecionado.setPaginas(paginas);

                if (livroDAO.update(livroSelecionado)) {
                    mostrarMensagem("Livro atualizado com sucesso!");
                    int selectedIndex = cbLivros.getSelectionModel().getSelectedIndex();
                    carregarLivros();
                    cbLivros.getSelectionModel().select(selectedIndex);
                } else {
                    mostrarErro("Falha ao atualizar livro.");
                }
            }
        } catch (NumberFormatException e) {
            mostrarErro("Número de páginas deve ser um valor numérico inteiro.");
        } catch (SQLException e) {
            mostrarErro("Erro no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void deletarLivro(ActionEvent event) {
        if (livroSelecionado != null) {
            try {
                if (livroDAO.delete(livroSelecionado.getId())) {
                    mostrarMensagem("Livro deletado: " + livroSelecionado.getTitulo());
                    carregarLivros();
                    cbLivros.getSelectionModel().select(null);
                } else {
                    mostrarErro("Falha ao deletar livro.");
                }
            } catch (SQLException e) {
                mostrarErro("Erro ao deletar livro: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            mostrarErro("Selecione um livro para deletar.");
        }
    }

    @FXML
    void folhear(ActionEvent event) {
        Livro livroParaAcao = null;
        if (livroSelecionado != null) {
            livroParaAcao = livroSelecionado;
        } else if (camposPreenchidosParaAcao()) {
            try {
                livroParaAcao = new Livro(
                        txtTituloLivro.getText(),
                        txtAutorLivro.getText(),
                        Integer.parseInt(txtPaginasLivro.getText())
                );
            } catch (NumberFormatException e) {
                mostrarErro("Número de páginas inválido para esta ação.");
                return;
            }
        }

        if (livroParaAcao != null) {
            txtResultado.setText(livroParaAcao.folhear());
            errorLabel.setText("");
        } else {
            mostrarErro("Preencha os campos (Título, Autor, Páginas) ou selecione um livro.");
        }
    }

    @FXML
    void ler(ActionEvent event) {
        Livro livroParaAcao = null;
        if (livroSelecionado != null) {
            livroParaAcao = livroSelecionado;
        } else if (camposPreenchidosParaAcao()) { // Reutiliza a validação de campos preenchidos
            try {
                livroParaAcao = new Livro(
                        txtTituloLivro.getText(),
                        txtAutorLivro.getText(),
                        Integer.parseInt(txtPaginasLivro.getText()) // Necessário para construir o objeto
                );
            } catch (NumberFormatException e) {
                mostrarErro("Número de páginas inválido para esta ação.");
                return;
            }
        }

        if (livroParaAcao != null) {
            txtResultado.setText(livroParaAcao.ler());
            errorLabel.setText("");
        } else {
            mostrarErro("Preencha os campos (Título, Autor, Páginas) ou selecione um livro.");
        }
    }

    private boolean validarCampos() {
        String titulo = txtTituloLivro.getText();
        String autor = txtAutorLivro.getText();
        String paginasStr = txtPaginasLivro.getText();

        if (titulo == null || titulo.trim().isEmpty() ||
                autor == null || autor.trim().isEmpty() ||
                paginasStr == null || paginasStr.trim().isEmpty()) {
            mostrarErro("Preencha todos os campos (Título, Autor, Páginas).");
            return false;
        }
        try {
            Integer.parseInt(paginasStr);
        } catch (NumberFormatException e) {
            mostrarErro("Número de páginas deve ser um valor numérico inteiro.");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    private boolean camposPreenchidosParaAcao() {
        return !(txtTituloLivro.getText().trim().isEmpty() ||
                txtAutorLivro.getText().trim().isEmpty() ||
                txtPaginasLivro.getText().trim().isEmpty());
    }

    private void limparCampos() {
        txtTituloLivro.clear();
        txtAutorLivro.clear();
        txtPaginasLivro.clear();
    }

    private void mostrarMensagem(String mensagem) {
        txtResultado.setText(mensagem);
        errorLabel.setText("");
    }

    private void mostrarErro(String mensagem) {
        errorLabel.setText("Erro: " + mensagem);
    }

    @FXML
    private void VoltarMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FormMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}