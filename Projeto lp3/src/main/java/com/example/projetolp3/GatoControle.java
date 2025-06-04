package com.example.projetolp3;

import com.example.projetolp3.Model.Gato;
import com.example.projetolp3.DAO.GatoDAO;
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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

public class GatoControle {

    @FXML private TextField txtRacaGato;
    @FXML private TextField txtCorGato;
    @FXML private TextField txtPesoGato;
    @FXML private TextArea txtResultado;
    @FXML private ComboBox<Gato> cbGatos;
    @FXML private Label errorLabel;
    @FXML private Button btnSalvar;
    @FXML private Button btnDeletarGato;
    @FXML private Button btnMiarGato;
    @FXML private Button btnArranharGato;

    private GatoDAO gatoDAO;
    private Gato gatoSelecionado;
    private final DecimalFormat df;

    public GatoControle() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US); // Força ponto para parse
        df = new DecimalFormat("#0.00", symbols);
    }

    @FXML
    public void initialize() {
        try {
            gatoDAO = new GatoDAO();
            gatoDAO.createTable();
            carregarGatos();
            atualizarInterface();
        } catch (SQLException e) {
            mostrarErro("Erro ao conectar com o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void carregarGatos() {
        try {
            ObservableList<Gato> gatos = FXCollections.observableArrayList(gatoDAO.getAll());
            cbGatos.setItems(gatos);

            cbGatos.setCellFactory(param -> new ListCell<Gato>() {
                @Override
                protected void updateItem(Gato gato, boolean empty) {
                    super.updateItem(gato, empty);
                    if (empty || gato == null) {
                        setText(null);
                    } else {
                        setText(gato.getRaca() + " - " + gato.getCor() + " (" + df.format(gato.getPeso()) + " kg)");
                    }
                }
            });

            cbGatos.setButtonCell(new ListCell<Gato>() {
                @Override
                protected void updateItem(Gato gato, boolean empty) {
                    super.updateItem(gato, empty);
                    if (empty || gato == null) {
                        setText("Selecione um gato");
                    } else {
                        setText(gato.getRaca() + " - " + gato.getCor());
                    }
                }
            });

            cbGatos.getSelectionModel().selectedItemProperty().addListener(
                    (obs, oldVal, newVal) -> {
                        gatoSelecionado = newVal;
                        atualizarInterface();
                    });
        } catch (SQLException e) {
            mostrarErro("Erro ao carregar gatos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void atualizarInterface() {
        if (gatoSelecionado != null) {
            txtRacaGato.setText(gatoSelecionado.getRaca());
            txtCorGato.setText(gatoSelecionado.getCor());
            txtPesoGato.setText(df.format(gatoSelecionado.getPeso()));
            btnSalvar.setText("Atualizar");
            txtResultado.setText("Editando: " + gatoSelecionado.getRaca() + " - " + gatoSelecionado.getCor());
        } else {
            limparCampos();
            btnSalvar.setText("Salvar");
            txtResultado.setText("Preencha os dados para cadastrar um novo gato ou selecione um para editar.");
        }
    }

    @FXML
    private void salvar(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }
        try {
            Number pesoNumber = df.parse(txtPesoGato.getText().replace(",","."));
            double peso = pesoNumber.doubleValue();

            if (gatoSelecionado == null) {
                Gato novoGato = new Gato(
                        txtRacaGato.getText(),
                        txtCorGato.getText(),
                        peso
                );
                if (gatoDAO.insert(novoGato)) {
                    mostrarMensagem("Gato cadastrado com sucesso!");
                    carregarGatos();
                    limparCampos();
                } else {
                    mostrarErro("Falha ao cadastrar gato.");
                }
            } else {
                gatoSelecionado.setRaca(txtRacaGato.getText());
                gatoSelecionado.setCor(txtCorGato.getText());
                gatoSelecionado.setPeso(peso);

                if (gatoDAO.update(gatoSelecionado)) {
                    mostrarMensagem("Gato atualizado com sucesso!");
                    int selectedIndex = cbGatos.getSelectionModel().getSelectedIndex();
                    carregarGatos();
                    cbGatos.getSelectionModel().select(selectedIndex);
                } else {
                    mostrarErro("Falha ao atualizar gato.");
                }
            }
        } catch (ParseException e) {
            mostrarErro("Formato de peso inválido. Use ##.## ou ##,##");
        } catch (SQLException e) {
            mostrarErro("Erro no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void deletarGato(ActionEvent event) {
        if (gatoSelecionado != null) {
            try {
                if (gatoDAO.delete(gatoSelecionado.getId())) {
                    mostrarMensagem("Gato deletado: " + gatoSelecionado.getRaca() + " - " + gatoSelecionado.getCor());
                    carregarGatos();
                    cbGatos.getSelectionModel().select(null);
                } else {
                    mostrarErro("Falha ao deletar gato.");
                }
            } catch (SQLException e) {
                mostrarErro("Erro ao deletar gato: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            mostrarErro("Selecione um gato para deletar.");
        }
    }

    @FXML
    void miar(ActionEvent event) {
        if (gatoSelecionado != null) {
            txtResultado.setText(gatoSelecionado.miar());
            errorLabel.setText("");
        } else if (camposPreenchidosParaAcao()) {
            try {
                Gato tempGato = new Gato(
                        txtRacaGato.getText(),
                        txtCorGato.getText(),
                        df.parse(txtPesoGato.getText().replace(",",".")).doubleValue()
                );
                txtResultado.setText(tempGato.miar());
                errorLabel.setText("");
            } catch (ParseException e) {
                mostrarErro("O peso deve ser um número válido para esta ação (##.## ou ##,##).");
            }
        } else {
            mostrarErro("Preencha os campos (Raça, Cor, Peso) ou selecione um gato.");
        }
    }

    @FXML
    void arranhar(ActionEvent event) {
        if (gatoSelecionado != null) {
            txtResultado.setText(gatoSelecionado.arranhar());
            errorLabel.setText("");
        } else if (camposPreenchidosParaAcao()) {
            try {
                Gato tempGato = new Gato(
                        txtRacaGato.getText(),
                        txtCorGato.getText(),
                        df.parse(txtPesoGato.getText().replace(",",".")).doubleValue()
                );
                txtResultado.setText(tempGato.arranhar());
                errorLabel.setText("");
            } catch (ParseException e) { // Mesmo que arranhar não use peso, o construtor temporário pode precisar
                mostrarErro("O peso deve ser um número válido para construir o gato temporário (##.## ou ##,##).");
            }
        } else {
            mostrarErro("Preencha os campos (Raça, Cor, Peso) ou selecione um gato.");
        }
    }

    private boolean validarCampos() {
        String raca = txtRacaGato.getText();
        String cor = txtCorGato.getText();
        String pesoStr = txtPesoGato.getText();

        if (raca == null || raca.trim().isEmpty() ||
                cor == null || cor.trim().isEmpty() ||
                pesoStr == null || pesoStr.trim().isEmpty()) {
            mostrarErro("Preencha todos os campos (Raça, Cor, Peso).");
            return false;
        }
        try {
            df.parse(pesoStr.replace(",",".")).doubleValue();
        } catch (ParseException e) {
            mostrarErro("Peso deve ser um número válido (ex: 2.5 ou 2,5).");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    private boolean camposPreenchidosParaAcao() {
        return !(txtRacaGato.getText().trim().isEmpty() ||
                txtCorGato.getText().trim().isEmpty() ||
                txtPesoGato.getText().trim().isEmpty());
    }

    private void limparCampos() {
        txtRacaGato.clear();
        txtCorGato.clear();
        txtPesoGato.clear();
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