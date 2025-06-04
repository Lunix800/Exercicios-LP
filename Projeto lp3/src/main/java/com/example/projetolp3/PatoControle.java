package com.example.projetolp3;

import com.example.projetolp3.Model.Pato;
import com.example.projetolp3.DAO.PatoDAO;
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

public class PatoControle {

    @FXML private TextField txtRacaPato; // Este campo corresponde a 'especie' no modelo Pato
    @FXML private TextField txtCorPato;
    @FXML private TextField txtPesoPato;
    @FXML private TextArea txtResultado;
    @FXML private ComboBox<Pato> cbPatos; // Adicionado
    @FXML private Label errorLabel;
    @FXML private Button btnSalvar;         // Adicionado
    @FXML private Button btnDeletarPato;    // Adicionado
    @FXML private Button btnNadar;          // Mantido
    @FXML private Button btnVoar;           // Mantido

    private PatoDAO patoDAO;
    private Pato patoSelecionado;
    private final DecimalFormat df;

    public PatoControle() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        df = new DecimalFormat("#0.00", symbols);
    }

    @FXML
    public void initialize() {
        try {
            patoDAO = new PatoDAO();
            patoDAO.createTable();
            carregarPatos();
            atualizarInterface();
        } catch (SQLException e) {
            mostrarErro("Erro ao conectar com o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void carregarPatos() {
        try {
            ObservableList<Pato> patos = FXCollections.observableArrayList(patoDAO.getAll());
            cbPatos.setItems(patos);

            cbPatos.setCellFactory(param -> new ListCell<Pato>() {
                @Override
                protected void updateItem(Pato pato, boolean empty) {
                    super.updateItem(pato, empty);
                    if (empty || pato == null) {
                        setText(null);
                    } else {
                        setText(pato.getEspecie() + " - " + pato.getCor() + " (" + df.format(pato.getPeso()) + " kg)");
                    }
                }
            });

            cbPatos.setButtonCell(new ListCell<Pato>() {
                @Override
                protected void updateItem(Pato pato, boolean empty) {
                    super.updateItem(pato, empty);
                    if (empty || pato == null) {
                        setText("Selecione um pato");
                    } else {
                        setText(pato.getEspecie() + " - " + pato.getCor());
                    }
                }
            });

            cbPatos.getSelectionModel().selectedItemProperty().addListener(
                    (obs, oldVal, newVal) -> {
                        patoSelecionado = newVal;
                        atualizarInterface();
                    });
        } catch (SQLException e) {
            mostrarErro("Erro ao carregar patos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void atualizarInterface() {
        if (patoSelecionado != null) {
            txtRacaPato.setText(patoSelecionado.getEspecie()); // especie -> txtRacaPato
            txtCorPato.setText(patoSelecionado.getCor());
            txtPesoPato.setText(df.format(patoSelecionado.getPeso()));
            btnSalvar.setText("Atualizar");
            txtResultado.setText("Editando: " + patoSelecionado.getEspecie() + " - " + patoSelecionado.getCor());
        } else {
            limparCampos();
            btnSalvar.setText("Salvar");
            txtResultado.setText("Preencha os dados para cadastrar um novo pato ou selecione um para editar.");
        }
    }

    @FXML
    private void salvar(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }
        try {
            double peso = df.parse(txtPesoPato.getText().replace(",", ".")).doubleValue();
            String especie = txtRacaPato.getText(); // txtRacaPato -> especie
            String cor = txtCorPato.getText();

            if (patoSelecionado == null) { // Criar novo
                Pato novoPato = new Pato(especie, cor, peso);
                if (patoDAO.insert(novoPato)) {
                    mostrarMensagem("Pato cadastrado com sucesso!");
                    carregarPatos();
                    limparCampos();
                } else {
                    mostrarErro("Falha ao cadastrar pato.");
                }
            } else { // Atualizar existente
                patoSelecionado.setEspecie(especie);
                patoSelecionado.setCor(cor);
                patoSelecionado.setPeso(peso);

                if (patoDAO.update(patoSelecionado)) {
                    mostrarMensagem("Pato atualizado com sucesso!");
                    int selectedIndex = cbPatos.getSelectionModel().getSelectedIndex();
                    carregarPatos();
                    cbPatos.getSelectionModel().select(selectedIndex);
                } else {
                    mostrarErro("Falha ao atualizar pato.");
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
    private void deletarPato(ActionEvent event) {
        if (patoSelecionado != null) {
            try {
                if (patoDAO.delete(patoSelecionado.getId())) {
                    mostrarMensagem("Pato deletado: " + patoSelecionado.getEspecie() + " - " + patoSelecionado.getCor());
                    carregarPatos();
                    cbPatos.getSelectionModel().select(null);
                } else {
                    mostrarErro("Falha ao deletar pato.");
                }
            } catch (SQLException e) {
                mostrarErro("Erro ao deletar pato: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            mostrarErro("Selecione um pato para deletar.");
        }
    }

    @FXML
    void nadar(ActionEvent event) {
        Pato patoParaAcao = null;
        if (patoSelecionado != null) {
            patoParaAcao = patoSelecionado;
        } else if (camposPreenchidosParaAcao()) {
            try {
                patoParaAcao = new Pato(
                        txtRacaPato.getText(), // Mapeando para especie
                        txtCorPato.getText(),
                        df.parse(txtPesoPato.getText().replace(",", ".")).doubleValue()
                );
            } catch (ParseException e) {
                mostrarErro("Peso inválido para esta ação (##.## ou ##,##).");
                return;
            }
        }

        if (patoParaAcao != null) {
            txtResultado.setText(patoParaAcao.nadar());
            errorLabel.setText("");
        } else {
            mostrarErro("Preencha os campos (Raça/Espécie, Cor, Peso) ou selecione um pato.");
        }
    }

    @FXML
    void voar(ActionEvent event) {
        Pato patoParaAcao = null;
        if (patoSelecionado != null) {
            patoParaAcao = patoSelecionado;
        } else if (camposPreenchidosParaAcao()) {
            try {
                patoParaAcao = new Pato(
                        txtRacaPato.getText(), // Mapeando para especie
                        txtCorPato.getText(),
                        df.parse(txtPesoPato.getText().replace(",", ".")).doubleValue()
                );
            } catch (ParseException e) {
                mostrarErro("Peso inválido para esta ação (##.## ou ##,##).");
                return;
            }
        }

        if (patoParaAcao != null) {
            txtResultado.setText(patoParaAcao.voar());
            errorLabel.setText("");
        } else {
            mostrarErro("Preencha os campos (Raça/Espécie, Cor, Peso) ou selecione um pato.");
        }
    }

    private boolean validarCampos() {
        String especie = txtRacaPato.getText(); // txtRacaPato -> especie
        String cor = txtCorPato.getText();
        String pesoStr = txtPesoPato.getText();

        if (especie == null || especie.trim().isEmpty() ||
                cor == null || cor.trim().isEmpty() ||
                pesoStr == null || pesoStr.trim().isEmpty()) {
            mostrarErro("Preencha todos os campos (Raça/Espécie, Cor, Peso).");
            return false;
        }
        try {
            df.parse(pesoStr.replace(",", ".")).doubleValue();
        } catch (ParseException e) {
            mostrarErro("Peso deve ser um número válido (ex: 2.5 ou 2,5).");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    private boolean camposPreenchidosParaAcao() {
        return !(txtRacaPato.getText().trim().isEmpty() || // txtRacaPato -> especie
                txtCorPato.getText().trim().isEmpty() ||
                txtPesoPato.getText().trim().isEmpty());
    }

    private void limparCampos() {
        txtRacaPato.clear(); // txtRacaPato -> especie
        txtCorPato.clear();
        txtPesoPato.clear();
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