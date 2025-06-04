package com.example.projetolp3;

import com.example.projetolp3.Model.Cachorro;
import com.example.projetolp3.DAO.CachorroDAO;
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

public class CachorroControle {
    @FXML private TextField txtNome;
    @FXML private TextField txtRaca;
    @FXML private TextField txtIdade;
    @FXML private TextArea txtResultado;
    @FXML private ComboBox<Cachorro> cbCachorros;
    @FXML private Label errorLabel;
    @FXML private Button btnSalvar;

    private CachorroDAO cachorroDAO;
    private Cachorro cachorroSelecionado;

    @FXML
    public void initialize() {
        try {
            cachorroDAO = new CachorroDAO();
            cachorroDAO.createTable();
            carregarCachorros();
        } catch (SQLException e) {
            mostrarErro("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }

    private void carregarCachorros() throws SQLException {
        ObservableList<Cachorro> cachorros = FXCollections.observableArrayList(cachorroDAO.getAll());
        cbCachorros.setItems(cachorros);

        cbCachorros.setCellFactory(param -> new ListCell<Cachorro>() {
            @Override
            protected void updateItem(Cachorro cachorro, boolean empty) {
                super.updateItem(cachorro, empty);
                setText(empty || cachorro == null ? null :
                        cachorro.getNome() + " - " + cachorro.getRaca() + " (" + cachorro.getIdade() + " anos)");
            }
        });

        cbCachorros.setButtonCell(new ListCell<Cachorro>() {
            @Override
            protected void updateItem(Cachorro cachorro, boolean empty) {
                super.updateItem(cachorro, empty);
                setText(empty || cachorro == null ? "Selecione um cachorro" :
                        cachorro.getNome() + " - " + cachorro.getRaca());
            }
        });

        cbCachorros.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    cachorroSelecionado = newVal;
                    atualizarInterface();
                });
    }

    private void atualizarInterface() {
        if (cachorroSelecionado != null) {
            txtNome.setText(cachorroSelecionado.getNome());
            txtRaca.setText(cachorroSelecionado.getRaca());
            txtIdade.setText(String.valueOf(cachorroSelecionado.getIdade()));
            btnSalvar.setText("Atualizar");
            txtResultado.setText("Editando: " + cachorroSelecionado.getNome());
        } else {
            limparCampos();
            btnSalvar.setText("Salvar");
            txtResultado.setText("Preencha os dados para cadastrar um novo cachorro");
        }
    }

    @FXML
    private void salvar(ActionEvent event) {
        try {
            if (validarCampos()) {
                if (cachorroSelecionado == null) {
                    Cachorro novoCachorro = new Cachorro(
                            txtNome.getText(),
                            txtRaca.getText(),
                            Integer.parseInt(txtIdade.getText())
                    );

                    if (cachorroDAO.insert(novoCachorro)) {
                        mostrarMensagem("Cachorro cadastrado com sucesso!");
                        carregarCachorros();
                        limparCampos();
                    }
                } else {
                    cachorroSelecionado.setNome(txtNome.getText());
                    cachorroSelecionado.setRaca(txtRaca.getText());
                    cachorroSelecionado.setIdade(Integer.parseInt(txtIdade.getText()));

                    if (cachorroDAO.update(cachorroSelecionado)) {
                        mostrarMensagem("Cachorro atualizado com sucesso!");
                        carregarCachorros();
                        limparCampos();
                        cbCachorros.getSelectionModel().clearSelection();
                    }
                }
            }
        } catch (NumberFormatException e) {
            mostrarErro("A idade deve ser um número válido");
        } catch (SQLException e) {
            mostrarErro("Erro no banco de dados: " + e.getMessage());
        }
    }

    @FXML
    private void deletarCachorro(ActionEvent event) {
        if (cachorroSelecionado != null) {
            try {
                if (cachorroDAO.delete(cachorroSelecionado.getId())) {
                    mostrarMensagem("Cachorro deletado: " + cachorroSelecionado.getNome());
                    carregarCachorros();
                    limparCampos();
                    cbCachorros.getSelectionModel().clearSelection();
                }
            } catch (SQLException e) {
                mostrarErro("Erro ao deletar cachorro: " + e.getMessage());
            }
        } else {
            mostrarErro("Selecione um cachorro para deletar");
        }
    }

    @FXML
    private void nome(ActionEvent event) {
        if (cachorroSelecionado != null) {
            txtResultado.setText(cachorroSelecionado.nome());
        } else {
            mostrarErro("Selecione um cachorro primeiro");
        }
    }

    @FXML
    private void latir(ActionEvent event) {
        if (cachorroSelecionado != null) {
            txtResultado.setText(cachorroSelecionado.latir());
        } else {
            mostrarErro("Selecione um cachorro primeiro");
        }
    }

    @FXML
    private void VoltarMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FormMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private boolean validarCampos() {
        if (txtNome.getText().isEmpty() || txtRaca.getText().isEmpty() || txtIdade.getText().isEmpty()) {
            mostrarErro("Preencha todos os campos");
            return false;
        }

        try {
            Integer.parseInt(txtIdade.getText());
        } catch (NumberFormatException e) {
            mostrarErro("Idade deve ser um número válido");
            return false;
        }

        return true;
    }

    private void limparCampos() {
        txtNome.clear();
        txtRaca.clear();
        txtIdade.clear();
    }

    private void mostrarMensagem(String mensagem) {
        txtResultado.setText(mensagem);
        errorLabel.setText("");
    }

    private void mostrarErro(String mensagem) {
        errorLabel.setText(mensagem);
    }
}