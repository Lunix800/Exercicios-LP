package com.example.projetolp3;

import com.example.projetolp3.Model.Galinha;
import com.example.projetolp3.DAO.GalinhaDAO;
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

public class GalinhaControle {

    @FXML private TextField txtRacaGalinha;
    @FXML private TextField txtCorGalinha;
    @FXML private TextField txtPesoGalinha;
    @FXML private TextArea txtResultado;
    @FXML private ComboBox<Galinha> cbGalinhas;
    @FXML private Label errorLabel;
    @FXML private Button btnSalvar;
    @FXML private Button btnDeletarGalinha; // Adicionado fx:id no FXML
    @FXML private Button btnCacarejarGalinha;
    @FXML private Button btnPesoGalinha;


    private GalinhaDAO galinhaDAO;
    private Galinha galinhaSelecionada;
    private final DecimalFormat df;

    public GalinhaControle() {
        // Configura o DecimalFormat para usar ponto como separador decimal para parse e vírgula para display se necessário
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US); // Força ponto para parse
        df = new DecimalFormat("#0.00", symbols); // Duas casas decimais
    }


    @FXML
    public void initialize() {
        try {
            galinhaDAO = new GalinhaDAO();
            galinhaDAO.createTable();
            carregarGalinhas();
            atualizarInterface();
        } catch (SQLException e) {
            mostrarErro("Erro ao conectar com o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void carregarGalinhas() {
        try {
            ObservableList<Galinha> galinhas = FXCollections.observableArrayList(galinhaDAO.getAll());
            cbGalinhas.setItems(galinhas);

            cbGalinhas.setCellFactory(param -> new ListCell<Galinha>() {
                @Override
                protected void updateItem(Galinha galinha, boolean empty) {
                    super.updateItem(galinha, empty);
                    if (empty || galinha == null) {
                        setText(null);
                    } else {
                        setText(galinha.getRaca() + " - " + galinha.getCor() + " (" + df.format(galinha.getPesogalinha()) + " kg)");
                    }
                }
            });

            cbGalinhas.setButtonCell(new ListCell<Galinha>() {
                @Override
                protected void updateItem(Galinha galinha, boolean empty) {
                    super.updateItem(galinha, empty);
                    if (empty || galinha == null) {
                        setText("Selecione uma galinha");
                    } else {
                        setText(galinha.getRaca() + " - " + galinha.getCor());
                    }
                }
            });

            cbGalinhas.getSelectionModel().selectedItemProperty().addListener(
                    (obs, oldVal, newVal) -> {
                        galinhaSelecionada = newVal;
                        atualizarInterface();
                    });
        } catch (SQLException e) {
            mostrarErro("Erro ao carregar galinhas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void atualizarInterface() {
        if (galinhaSelecionada != null) {
            txtRacaGalinha.setText(galinhaSelecionada.getRaca());
            txtCorGalinha.setText(galinhaSelecionada.getCor());
            txtPesoGalinha.setText(df.format(galinhaSelecionada.getPesogalinha())); // Formata para exibição
            btnSalvar.setText("Atualizar");
            txtResultado.setText("Editando: " + galinhaSelecionada.getRaca() + " - " + galinhaSelecionada.getCor());
        } else {
            limparCampos();
            btnSalvar.setText("Salvar");
            txtResultado.setText("Preencha os dados para cadastrar uma nova galinha ou selecione uma para editar.");
        }
    }

    @FXML
    private void salvar(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }
        try {
            // Parse do peso usando o formato que aceita ponto ou vírgula
            Number pesoNumber = df.parse(txtPesoGalinha.getText().replace(",","."));
            double peso = pesoNumber.doubleValue();

            if (galinhaSelecionada == null) {
                Galinha novaGalinha = new Galinha(
                        txtRacaGalinha.getText(),
                        txtCorGalinha.getText(),
                        peso
                );
                if (galinhaDAO.insert(novaGalinha)) {
                    mostrarMensagem("Galinha cadastrada com sucesso!");
                    carregarGalinhas();
                    limparCampos();
                } else {
                    mostrarErro("Falha ao cadastrar galinha.");
                }
            } else {
                galinhaSelecionada.setRaca(txtRacaGalinha.getText());
                galinhaSelecionada.setCor(txtCorGalinha.getText());
                galinhaSelecionada.setPesogalinha(peso);

                if (galinhaDAO.update(galinhaSelecionada)) {
                    mostrarMensagem("Galinha atualizada com sucesso!");
                    int selectedIndex = cbGalinhas.getSelectionModel().getSelectedIndex(); // Salva o índice
                    carregarGalinhas();
                    cbGalinhas.getSelectionModel().select(selectedIndex); // Restaura a seleção para ver a atualização
                    // ou cbGalinhas.getSelectionModel().select(null); se preferir limpar a seleção
                } else {
                    mostrarErro("Falha ao atualizar galinha.");
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
    private void deletarGalinha(ActionEvent event) {
        if (galinhaSelecionada != null) {
            try {
                if (galinhaDAO.delete(galinhaSelecionada.getId())) {
                    mostrarMensagem("Galinha deletada: " + galinhaSelecionada.getRaca() + " - " + galinhaSelecionada.getCor());
                    carregarGalinhas();
                    cbGalinhas.getSelectionModel().select(null);
                } else {
                    mostrarErro("Falha ao deletar galinha.");
                }
            } catch (SQLException e) {
                mostrarErro("Erro ao deletar galinha: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            mostrarErro("Selecione uma galinha para deletar.");
        }
    }

    @FXML
    void cacarejar(ActionEvent event) {
        if (galinhaSelecionada != null) {
            txtResultado.setText(galinhaSelecionada.cacarejar());
            errorLabel.setText("");
        } else if (camposPreenchidosParaAcao()) {
            try {
                Galinha tempGalinha = new Galinha(
                        txtRacaGalinha.getText(),
                        txtCorGalinha.getText(),
                        df.parse(txtPesoGalinha.getText().replace(",",".")).doubleValue()
                );
                txtResultado.setText(tempGalinha.cacarejar());
                errorLabel.setText("");
            } catch (ParseException e) {
                mostrarErro("O peso deve ser um número válido para esta ação (##.## ou ##,##).");
            }
        } else {
            mostrarErro("Preencha os campos (Raça, Cor, Peso) ou selecione uma galinha.");
        }
    }

    @FXML
    void peso(ActionEvent event) {
        if (galinhaSelecionada != null) {
            txtResultado.setText(galinhaSelecionada.peso());
            errorLabel.setText("");
        } else if (camposPreenchidosParaAcao()) {
            try {
                Galinha tempGalinha = new Galinha(
                        txtRacaGalinha.getText(),
                        txtCorGalinha.getText(),
                        df.parse(txtPesoGalinha.getText().replace(",",".")).doubleValue()
                );
                txtResultado.setText(tempGalinha.peso());
                errorLabel.setText("");
            } catch (ParseException e) {
                mostrarErro("O peso deve ser um número válido para esta ação (##.## ou ##,##).");
            }
        } else {
            mostrarErro("Preencha os campos (Raça, Cor, Peso) ou selecione uma galinha.");
        }
    }

    private boolean validarCampos() {
        String raca = txtRacaGalinha.getText();
        String cor = txtCorGalinha.getText();
        String pesoStr = txtPesoGalinha.getText();

        if (raca == null || raca.trim().isEmpty() ||
                cor == null || cor.trim().isEmpty() ||
                pesoStr == null || pesoStr.trim().isEmpty()) {
            mostrarErro("Preencha todos os campos (Raça, Cor, Peso).");
            return false;
        }
        try {
            // Apenas verifica se é um double válido, o parse efetivo é feito no método salvar
            df.parse(pesoStr.replace(",",".")).doubleValue();
        } catch (ParseException e) {
            mostrarErro("Peso deve ser um número válido (ex: 2.5 ou 2,5).");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    private boolean camposPreenchidosParaAcao() {
        return !(txtRacaGalinha.getText().trim().isEmpty() ||
                txtCorGalinha.getText().trim().isEmpty() ||
                txtPesoGalinha.getText().trim().isEmpty());
    }

    private void limparCampos() {
        txtRacaGalinha.clear();
        txtCorGalinha.clear();
        txtPesoGalinha.clear();
        // txtResultado.clear(); // Opcional, depende se quer limpar a mensagem de status
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
        // Verifique se o FormMenu.fxml está no mesmo pacote ou ajuste o caminho
        Parent root = FXMLLoader.load(getClass().getResource("FormMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}