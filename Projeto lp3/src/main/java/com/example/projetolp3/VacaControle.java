package com.example.projetolp3;

import com.example.projetolp3.Model.Vaca;
import com.example.projetolp3.DAO.VacaDAO;
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

public class VacaControle {

    @FXML private TextField txtRacaVaca;
    @FXML private TextField txtCorVaca;
    @FXML private TextField txtPesoVaca;
    @FXML private TextArea txtResultado;
    @FXML private ComboBox<Vaca> cbVacas;       // Adicionado
    @FXML private Label errorLabel;
    @FXML private Button btnSalvar;           // Adicionado
    @FXML private Button btnDeletarVaca;      // Adicionado
    @FXML private Button txtComerVaca; // Mantido com o fx:id do seu FXML (para ação "comer")
    @FXML private Button btnMugirVaca;        // Mantido

    private VacaDAO vacaDAO;
    private Vaca vacaSelecionada;
    private final DecimalFormat df;

    public VacaControle() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        df = new DecimalFormat("#0.00", symbols);
    }

    @FXML
    public void initialize() {
        try {
            vacaDAO = new VacaDAO();
            vacaDAO.createTable();
            carregarVacas();
            atualizarInterface();
        } catch (SQLException e) {
            mostrarErro("Erro ao conectar com o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void carregarVacas() {
        try {
            ObservableList<Vaca> vacas = FXCollections.observableArrayList(vacaDAO.getAll());
            cbVacas.setItems(vacas);

            cbVacas.setCellFactory(param -> new ListCell<Vaca>() {
                @Override
                protected void updateItem(Vaca vaca, boolean empty) {
                    super.updateItem(vaca, empty);
                    if (empty || vaca == null) {
                        setText(null);
                    } else {
                        setText(vaca.getRaca() + " - " + vaca.getCor() + " (" + df.format(vaca.getPeso()) + " kg)");
                    }
                }
            });

            cbVacas.setButtonCell(new ListCell<Vaca>() {
                @Override
                protected void updateItem(Vaca vaca, boolean empty) {
                    super.updateItem(vaca, empty);
                    if (empty || vaca == null) {
                        setText("Selecione uma vaca");
                    } else {
                        setText(vaca.getRaca() + " - " + vaca.getCor());
                    }
                }
            });

            cbVacas.getSelectionModel().selectedItemProperty().addListener(
                    (obs, oldVal, newVal) -> {
                        vacaSelecionada = newVal;
                        atualizarInterface();
                    });
        } catch (SQLException e) {
            mostrarErro("Erro ao carregar vacas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void atualizarInterface() {
        if (vacaSelecionada != null) {
            txtRacaVaca.setText(vacaSelecionada.getRaca());
            txtCorVaca.setText(vacaSelecionada.getCor());
            txtPesoVaca.setText(df.format(vacaSelecionada.getPeso()));
            btnSalvar.setText("Atualizar");
            txtResultado.setText("Editando: " + vacaSelecionada.getRaca() + " - " + vacaSelecionada.getCor());
        } else {
            limparCampos();
            btnSalvar.setText("Salvar");
            txtResultado.setText("Preencha os dados para cadastrar uma nova vaca ou selecione uma para editar.");
        }
    }

    @FXML
    private void salvar(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }
        try {
            double peso = df.parse(txtPesoVaca.getText().replace(",", ".")).doubleValue();
            String raca = txtRacaVaca.getText();
            String cor = txtCorVaca.getText();

            if (vacaSelecionada == null) { // Criar nova
                Vaca novaVaca = new Vaca(raca, cor, peso);
                if (vacaDAO.insert(novaVaca)) {
                    mostrarMensagem("Vaca cadastrada com sucesso!");
                    carregarVacas();
                    limparCampos();
                } else {
                    mostrarErro("Falha ao cadastrar vaca.");
                }
            } else { // Atualizar existente
                vacaSelecionada.setRaca(raca);
                vacaSelecionada.setCor(cor);
                vacaSelecionada.setPeso(peso);

                if (vacaDAO.update(vacaSelecionada)) {
                    mostrarMensagem("Vaca atualizada com sucesso!");
                    int selectedIndex = cbVacas.getSelectionModel().getSelectedIndex();
                    carregarVacas();
                    cbVacas.getSelectionModel().select(selectedIndex);
                } else {
                    mostrarErro("Falha ao atualizar vaca.");
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
    private void deletarVaca(ActionEvent event) {
        if (vacaSelecionada != null) {
            try {
                if (vacaDAO.delete(vacaSelecionada.getId())) {
                    mostrarMensagem("Vaca deletada: " + vacaSelecionada.getRaca() + " - " + vacaSelecionada.getCor());
                    carregarVacas();
                    cbVacas.getSelectionModel().select(null);
                } else {
                    mostrarErro("Falha ao deletar vaca.");
                }
            } catch (SQLException e) {
                mostrarErro("Erro ao deletar vaca: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            mostrarErro("Selecione uma vaca para deletar.");
        }
    }

    @FXML
    void comer(ActionEvent event) {
        Vaca vacaParaAcao = null;
        if (vacaSelecionada != null) {
            vacaParaAcao = vacaSelecionada;
        } else if (camposPreenchidosParaAcao()) {
            try {
                vacaParaAcao = new Vaca(
                        txtRacaVaca.getText(),
                        txtCorVaca.getText(),
                        df.parse(txtPesoVaca.getText().replace(",", ".")).doubleValue()
                );
            } catch (ParseException e) {
                mostrarErro("Peso inválido para esta ação (##.## ou ##,##).");
                return;
            }
        }

        if (vacaParaAcao != null) {
            txtResultado.setText(vacaParaAcao.comer());
            errorLabel.setText("");
        } else {
            mostrarErro("Preencha os campos (Raça, Cor, Peso) ou selecione uma vaca.");
        }
    }

    @FXML
    void mugir(ActionEvent event) {
        Vaca vacaParaAcao = null;
        if (vacaSelecionada != null) {
            vacaParaAcao = vacaSelecionada;
        } else if (camposPreenchidosParaAcao()) {
            try {
                vacaParaAcao = new Vaca(
                        txtRacaVaca.getText(),
                        txtCorVaca.getText(),
                        df.parse(txtPesoVaca.getText().replace(",", ".")).doubleValue()
                );
            } catch (ParseException e) {
                mostrarErro("Peso inválido para esta ação (##.## ou ##,##).");
                return;
            }
        }

        if (vacaParaAcao != null) {
            txtResultado.setText(vacaParaAcao.mugir());
            errorLabel.setText("");
        } else {
            mostrarErro("Preencha os campos (Raça, Cor, Peso) ou selecione uma vaca.");
        }
    }

    private boolean validarCampos() {
        String raca = txtRacaVaca.getText();
        String cor = txtCorVaca.getText();
        String pesoStr = txtPesoVaca.getText();

        if (raca == null || raca.trim().isEmpty() ||
                cor == null || cor.trim().isEmpty() ||
                pesoStr == null || pesoStr.trim().isEmpty()) {
            mostrarErro("Preencha todos os campos (Raça, Cor, Peso).");
            return false;
        }
        try {
            df.parse(pesoStr.replace(",", ".")).doubleValue();
        } catch (ParseException e) {
            mostrarErro("Peso deve ser um número válido (ex: 200.5 ou 200,5).");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    private boolean camposPreenchidosParaAcao() {
        return !(txtRacaVaca.getText().trim().isEmpty() ||
                txtCorVaca.getText().trim().isEmpty() ||
                txtPesoVaca.getText().trim().isEmpty());
    }

    private void limparCampos() {
        txtRacaVaca.clear();
        txtCorVaca.clear();
        txtPesoVaca.clear();
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