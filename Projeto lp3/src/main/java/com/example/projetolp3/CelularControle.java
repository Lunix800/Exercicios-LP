package com.example.projetolp3;

import com.example.projetolp3.Model.Celular;
import com.example.projetolp3.DAO.CelularDAO;
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

public class CelularControle {

    @FXML private TextField txtMarcaCelular;
    @FXML private TextField txtModeloCelular;
    @FXML private TextField txtBateriaCelular;
    @FXML private TextArea txtResultadoCelular;
    @FXML private ComboBox<Celular> cbCelulares;
    @FXML private Label errorLabel;
    @FXML private Button btnSalvar;
    @FXML private Button btnMarcaCelular;    // Botão para ação "marca"
    @FXML private Button btnBateriaCelular; // Botão para ação "bateria"


    private CelularDAO celularDAO;
    private Celular celularSelecionado;

    @FXML
    public void initialize() {
        try {
            celularDAO = new CelularDAO();
            celularDAO.createTable();
            carregarCelulares();
            atualizarInterface(); // Para definir estado inicial da UI
        } catch (SQLException e) {
            mostrarErro("Erro ao conectar com o banco de dados: " + e.getMessage());
            e.printStackTrace(); // Para debug no console
        }
    }

    private void carregarCelulares() {
        try {
            ObservableList<Celular> celulares = FXCollections.observableArrayList(celularDAO.getAll());
            cbCelulares.setItems(celulares);

            cbCelulares.setCellFactory(param -> new ListCell<Celular>() {
                @Override
                protected void updateItem(Celular celular, boolean empty) {
                    super.updateItem(celular, empty);
                    setText(empty || celular == null ? null :
                            celular.getMarcacelular() + " " + celular.getModelo() + " (" + celular.getCapacidadeBateria() + "%)");
                }
            });

            cbCelulares.setButtonCell(new ListCell<Celular>() {
                @Override
                protected void updateItem(Celular celular, boolean empty) {
                    super.updateItem(celular, empty);
                    setText(empty || celular == null ? "Selecione um celular" :
                            celular.getMarcacelular() + " " + celular.getModelo());
                }
            });

            cbCelulares.getSelectionModel().selectedItemProperty().addListener(
                    (obs, oldVal, newVal) -> {
                        celularSelecionado = newVal;
                        atualizarInterface();
                    });
        } catch (SQLException e) {
            mostrarErro("Erro ao carregar celulares: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void atualizarInterface() {
        if (celularSelecionado != null) {
            txtMarcaCelular.setText(celularSelecionado.getMarcacelular());
            txtModeloCelular.setText(celularSelecionado.getModelo());
            txtBateriaCelular.setText(String.valueOf(celularSelecionado.getCapacidadeBateria()));
            btnSalvar.setText("Atualizar");
            txtResultadoCelular.setText("Editando: " + celularSelecionado.getMarcacelular() + " " + celularSelecionado.getModelo());
        } else {
            limparCampos();
            btnSalvar.setText("Salvar");
            txtResultadoCelular.setText("Preencha os dados para cadastrar um novo celular ou selecione um para editar.");
        }
    }

    @FXML
    private void salvar(ActionEvent event) {
        try {
            if (validarCampos()) {
                if (celularSelecionado == null) {
                    Celular novoCelular = new Celular(
                            txtMarcaCelular.getText(),
                            txtModeloCelular.getText(),
                            Integer.parseInt(txtBateriaCelular.getText())
                    );

                    if (celularDAO.insert(novoCelular)) {
                        mostrarMensagem("Celular cadastrado com sucesso!");
                        carregarCelulares();
                        limparCampos();
                        // cbCelulares.getSelectionModel().select(novoCelular); // Opcional: selecionar o recém-adicionado
                    } else {
                        mostrarErro("Falha ao cadastrar celular.");
                    }
                } else {
                    celularSelecionado.setMarcacelular(txtMarcaCelular.getText());
                    celularSelecionado.setModelo(txtModeloCelular.getText());
                    celularSelecionado.setCapacidadeBateria(Integer.parseInt(txtBateriaCelular.getText()));

                    if (celularDAO.update(celularSelecionado)) {
                        mostrarMensagem("Celular atualizado com sucesso!");
                        carregarCelulares(); // Recarrega para refletir a mudança no ComboBox visualmente
                        // cbCelulares.getSelectionModel().clearSelection(); // Desseleciona
                        // limparCampos(); // Limpa campos e seleção (atualizarInterface já faz isso se limpar a seleção)
                        cbCelulares.getSelectionModel().select(null); // Para reativar o listener e atualizarInterface
                    } else {
                        mostrarErro("Falha ao atualizar celular.");
                    }
                }
            }
        } catch (NumberFormatException e) {
            mostrarErro("A capacidade da bateria deve ser um número válido.");
        } catch (SQLException e) {
            mostrarErro("Erro no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void deletarCelular(ActionEvent event) {
        if (celularSelecionado != null) {
            try {
                if (celularDAO.delete(celularSelecionado.getId())) {
                    mostrarMensagem("Celular deletado: " + celularSelecionado.getMarcacelular() + " " + celularSelecionado.getModelo());
                    carregarCelulares();
                    // cbCelulares.getSelectionModel().clearSelection(); // Desseleciona
                    // limparCampos(); // Limpa campos e seleção
                    cbCelulares.getSelectionModel().select(null); // Para reativar o listener e atualizarInterface
                } else {
                    mostrarErro("Falha ao deletar celular.");
                }
            } catch (SQLException e) {
                mostrarErro("Erro ao deletar celular: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            mostrarErro("Selecione um celular para deletar.");
        }
    }


    @FXML
    void bateria(ActionEvent event) {
        if (celularSelecionado != null) {
            txtResultadoCelular.setText(celularSelecionado.bateria());
            errorLabel.setText("");
        } else if (!txtBateriaCelular.getText().isEmpty() && !txtMarcaCelular.getText().isEmpty() && !txtModeloCelular.getText().isEmpty()){
            try {
                // Valida se o campo bateria é um número antes de tentar criar o objeto temporário
                int capBateria = Integer.parseInt(txtBateriaCelular.getText());
                Celular tempCelular = new Celular(
                        txtMarcaCelular.getText(),
                        txtModeloCelular.getText(),
                        capBateria
                );
                txtResultadoCelular.setText(tempCelular.bateria());
                errorLabel.setText("");
            } catch (NumberFormatException e) {
                mostrarErro("A capacidade da bateria deve ser um número válido para esta ação.");
            }
        } else {
            mostrarErro("Preencha os campos ou selecione um celular.");
        }
    }

    @FXML
    void marca(ActionEvent event) {
        if (celularSelecionado != null) {
            txtResultadoCelular.setText(celularSelecionado.marca());
            errorLabel.setText("");
        } else if (!txtMarcaCelular.getText().isEmpty() && !txtModeloCelular.getText().isEmpty() && !txtBateriaCelular.getText().isEmpty()){
            try {
                // Valida se o campo bateria é um número antes de tentar criar o objeto temporário
                int capBateria = Integer.parseInt(txtBateriaCelular.getText());
                Celular tempCelular = new Celular(
                        txtMarcaCelular.getText(),
                        txtModeloCelular.getText(),
                        capBateria
                );
                txtResultadoCelular.setText(tempCelular.marca());
                errorLabel.setText("");
            } catch (NumberFormatException e) {
                mostrarErro("A capacidade da bateria deve ser um número válido para esta ação.");
            }
        } else {
            mostrarErro("Preencha os campos ou selecione um celular.");
        }
    }

    private boolean validarCampos() {
        String marca = txtMarcaCelular.getText();
        String modelo = txtModeloCelular.getText();
        String capacidadeStr = txtBateriaCelular.getText();

        if (marca == null || marca.trim().isEmpty() ||
                modelo == null || modelo.trim().isEmpty() ||
                capacidadeStr == null || capacidadeStr.trim().isEmpty()) {
            mostrarErro("Preencha todos os campos (Marca, Modelo, %Bateria).");
            return false;
        }
        try {
            Integer.parseInt(capacidadeStr);
        } catch (NumberFormatException e) {
            mostrarErro("Capacidade da bateria deve ser um número válido.");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    private void limparCampos() {
        txtMarcaCelular.clear();
        txtModeloCelular.clear();
        txtBateriaCelular.clear();
        // txtResultadoCelular não é limpo aqui intencionalmente para manter mensagens de status.
        // A seleção do ComboBox é tratada em atualizarInterface.
    }

    private void mostrarMensagem(String mensagem) {
        txtResultadoCelular.setText(mensagem);
        errorLabel.setText("");
    }

    private void mostrarErro(String mensagem) {
        errorLabel.setText("Erro: " + mensagem);
        // txtResultadoCelular.clear(); // Opcional: limpar área de resultado em caso de erro
    }

    @FXML
    private void VoltarMenu(ActionEvent event) throws IOException {
        // Certifique-se de que FormMenu.fxml está no mesmo pacote que esta classe,
        // ou ajuste o caminho getResource.
        Parent root = FXMLLoader.load(getClass().getResource("FormMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}