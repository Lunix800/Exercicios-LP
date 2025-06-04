package com.example.projetolp3;

import com.example.projetolp3.Model.Carro;
import com.example.projetolp3.DAO.CarroDAO;
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

public class CarroControle {
    @FXML private TextField txtMarca;
    @FXML private TextField txtModelo;
    @FXML private TextField txtAno;
    @FXML private TextArea txtResultado;
    @FXML private ComboBox<Carro> cbCarros;
    @FXML private Label errorLabel;
    @FXML private Button btnSalvar;

    private CarroDAO carroDAO;
    private Carro carroSelecionado;

    @FXML
    public void initialize() {
        try {
            carroDAO = new CarroDAO();
            carroDAO.createTable();
            carregarCarros();
        } catch (SQLException e) {
            mostrarErro("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }

    private void carregarCarros() throws SQLException {
        ObservableList<Carro> carros = FXCollections.observableArrayList(carroDAO.getAll());
        cbCarros.setItems(carros);

        // Configura como os itens são exibidos na lista suspensa
        cbCarros.setCellFactory(param -> new ListCell<Carro>() {
            @Override
            protected void updateItem(Carro carro, boolean empty) {
                super.updateItem(carro, empty);
                setText(empty || carro == null ? null :
                        carro.getMarca() + " " + carro.getModelo() + " (" + carro.getAno() + ")");
            }
        });

        // Configura como o item selecionado é exibido no ComboBox
        cbCarros.setButtonCell(new ListCell<Carro>() {
            @Override
            protected void updateItem(Carro carro, boolean empty) {
                super.updateItem(carro, empty);
                setText(empty || carro == null ? "Selecione um carro" :
                        carro.getMarca() + " " + carro.getModelo());
            }
        });

        // Listener para quando selecionar um item no ComboBox
        cbCarros.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    carroSelecionado = newVal;
                    atualizarInterface();
                });
    }

    private void atualizarInterface() {
        if (carroSelecionado != null) {
            // Modo edição - preenche os campos
            txtMarca.setText(carroSelecionado.getMarca());
            txtModelo.setText(carroSelecionado.getModelo());
            txtAno.setText(String.valueOf(carroSelecionado.getAno()));
            btnSalvar.setText("Atualizar");
            txtResultado.setText("Editando: " + carroSelecionado.getModelo());
        } else {
            // Modo novo - limpa os campos
            limparCampos();
            btnSalvar.setText("Salvar");
            txtResultado.setText("Preencha os dados para cadastrar um novo carro");
        }
    }

    @FXML
    private void salvar(ActionEvent event) {
        try {
            if (validarCampos()) {
                if (carroSelecionado == null) {
                    // MODO CADASTRO - Criar novo carro
                    Carro novoCarro = new Carro(
                            txtMarca.getText(),
                            txtModelo.getText(),
                            Integer.parseInt(txtAno.getText())
                    );

                    if (carroDAO.insert(novoCarro)) {
                        mostrarMensagem("Carro cadastrado com sucesso!");
                        carregarCarros();
                        limparCampos();
                    }
                } else {
                    // MODO EDIÇÃO - Atualizar carro existente
                    carroSelecionado.setMarca(txtMarca.getText());
                    carroSelecionado.setModelo(txtModelo.getText());
                    carroSelecionado.setAno(Integer.parseInt(txtAno.getText()));

                    if (carroDAO.update(carroSelecionado)) {
                        mostrarMensagem("Carro atualizado com sucesso!");
                        carregarCarros();
                        limparCampos();
                        carroSelecionado = null; // Volta para modo cadastro
                        cbCarros.getSelectionModel().clearSelection();
                    }
                }
            }
        } catch (NumberFormatException e) {
            mostrarErro("O ano deve ser um número válido");
        } catch (SQLException e) {
            mostrarErro("Erro no banco de dados: " + e.getMessage());
        }
    }

    @FXML
    private void deletarCarro(ActionEvent event) {
        if (carroSelecionado != null) {
            try {
                if (carroDAO.delete(carroSelecionado.getId())) {
                    mostrarMensagem("Carro deletado: " + carroSelecionado.getModelo());
                    carregarCarros();
                    limparCampos();
                    carroSelecionado = null;
                    cbCarros.getSelectionModel().clearSelection();
                }
            } catch (SQLException e) {
                mostrarErro("Erro ao deletar carro: " + e.getMessage());
            }
        } else {
            mostrarErro("Selecione um carro para deletar");
        }
    }

    @FXML
    private void novoCarro(ActionEvent event) {
        carroSelecionado = null;
        cbCarros.getSelectionModel().clearSelection();
        limparCampos();
        mostrarMensagem("Pronto para cadastrar um novo carro");
    }

    @FXML
    private void acelerar(ActionEvent event) {
        if (carroSelecionado != null) {
            txtResultado.setText("VRUM VRUM! O " + carroSelecionado.getModelo() + " está acelerando!");
        } else {
            mostrarErro("Selecione um carro primeiro");
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
        if (txtMarca.getText().isEmpty() || txtModelo.getText().isEmpty() || txtAno.getText().isEmpty()) {
            mostrarErro("Preencha todos os campos");
            return false;
        }

        try {
            Integer.parseInt(txtAno.getText());
        } catch (NumberFormatException e) {
            mostrarErro("Ano deve ser um número válido");
            return false;
        }

        return true;
    }

    private void limparCampos() {
        txtMarca.clear();
        txtModelo.clear();
        txtAno.clear();
    }

    private void mostrarMensagem(String mensagem) {
        txtResultado.setText(mensagem);
        errorLabel.setText("");
    }

    private void mostrarErro(String mensagem) {
        errorLabel.setText(mensagem);
    }
}