package com.example.projetolp3;

import com.example.projetolp3.Model.Lanche;
import com.example.projetolp3.DAO.LancheDAO; // Importar o DAO
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

public class LancheControle {

    @FXML private TextField txtNomeLanche;
    @FXML private TextField txtPrecoLanche;
    @FXML private TextField txtTamanhoLanche;
    @FXML private TextArea txtResultado;
    @FXML private ComboBox<Lanche> cbLanches; // Adicionado
    @FXML private Label errorLabel;
    @FXML private Button btnSalvar; // Adicionado
    @FXML private Button btnDeletarLanche; // Adicionado
    @FXML private Button btnProntoLanche; // Mantido (servir)
    @FXML private Button btnPrecoLanche; // Mantido (preco - agora btnVerPreco)

    private LancheDAO lancheDAO;
    private Lanche lancheSelecionado;
    private final DecimalFormat df;

    public LancheControle() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        df = new DecimalFormat("#0.00", symbols);
    }

    @FXML
    public void initialize() {
        try {
            lancheDAO = new LancheDAO();
            lancheDAO.createTable();
            carregarLanches();
            atualizarInterface();
        } catch (SQLException e) {
            mostrarErro("Erro ao conectar com o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void carregarLanches() {
        try {
            ObservableList<Lanche> lanches = FXCollections.observableArrayList(lancheDAO.getAll());
            cbLanches.setItems(lanches);

            cbLanches.setCellFactory(param -> new ListCell<Lanche>() {
                @Override
                protected void updateItem(Lanche lanche, boolean empty) {
                    super.updateItem(lanche, empty);
                    if (empty || lanche == null) {
                        setText(null);
                    } else {
                        setText(lanche.getNome() + " (" + lanche.getTamanho() + ") - R$" + df.format(lanche.getPreco()));
                    }
                }
            });

            cbLanches.setButtonCell(new ListCell<Lanche>() {
                @Override
                protected void updateItem(Lanche lanche, boolean empty) {
                    super.updateItem(lanche, empty);
                    if (empty || lanche == null) {
                        setText("Selecione um lanche");
                    } else {
                        setText(lanche.getNome() + " (" + lanche.getTamanho() + ")");
                    }
                }
            });

            cbLanches.getSelectionModel().selectedItemProperty().addListener(
                    (obs, oldVal, newVal) -> {
                        lancheSelecionado = newVal;
                        atualizarInterface();
                    });
        } catch (SQLException e) {
            mostrarErro("Erro ao carregar lanches: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void atualizarInterface() {
        if (lancheSelecionado != null) {
            txtNomeLanche.setText(lancheSelecionado.getNome());
            txtPrecoLanche.setText(df.format(lancheSelecionado.getPreco()));
            txtTamanhoLanche.setText(lancheSelecionado.getTamanho());
            btnSalvar.setText("Atualizar");
            txtResultado.setText("Editando: " + lancheSelecionado.getNome());
        } else {
            limparCampos();
            btnSalvar.setText("Salvar");
            txtResultado.setText("Preencha os dados para cadastrar um novo lanche ou selecione um para editar.");
        }
    }

    @FXML
    private void salvar(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }
        try {
            double preco = df.parse(txtPrecoLanche.getText().replace(",",".")).doubleValue();
            String nome = txtNomeLanche.getText();
            String tamanho = txtTamanhoLanche.getText();

            if (lancheSelecionado == null) {
                Lanche novoLanche = new Lanche(nome, preco, tamanho);
                if (lancheDAO.insert(novoLanche)) {
                    mostrarMensagem("Lanche cadastrado com sucesso!");
                    carregarLanches();
                    limparCampos();
                } else {
                    mostrarErro("Falha ao cadastrar lanche.");
                }
            } else {
                lancheSelecionado.setNome(nome);
                lancheSelecionado.setPreco(preco);
                lancheSelecionado.setTamanho(tamanho);

                if (lancheDAO.update(lancheSelecionado)) {
                    mostrarMensagem("Lanche atualizado com sucesso!");
                    int selectedIndex = cbLanches.getSelectionModel().getSelectedIndex();
                    carregarLanches();
                    cbLanches.getSelectionModel().select(selectedIndex);
                } else {
                    mostrarErro("Falha ao atualizar lanche.");
                }
            }
        } catch (ParseException e) {
            mostrarErro("Formato de preço inválido. Use ##.## ou ##,##");
        } catch (SQLException e) {
            mostrarErro("Erro no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void deletarLanche(ActionEvent event) {
        if (lancheSelecionado != null) {
            try {
                if (lancheDAO.delete(lancheSelecionado.getId())) {
                    mostrarMensagem("Lanche deletado: " + lancheSelecionado.getNome());
                    carregarLanches();
                    cbLanches.getSelectionModel().select(null);
                } else {
                    mostrarErro("Falha ao deletar lanche.");
                }
            } catch (SQLException e) {
                mostrarErro("Erro ao deletar lanche: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            mostrarErro("Selecione um lanche para deletar.");
        }
    }

    // Método servir (ação do botão "Pronto")
    @FXML
    void servir(ActionEvent event) { // Nome original do seu FXML
        if (lancheSelecionado != null) {
            txtResultado.setText(lancheSelecionado.servir());
            errorLabel.setText("");
        } else if (camposPreenchidosParaAcao()) {
            try {
                Lanche tempLanche = new Lanche(
                        txtNomeLanche.getText(),
                        df.parse(txtPrecoLanche.getText().replace(",",".")).doubleValue(),
                        txtTamanhoLanche.getText()
                );
                txtResultado.setText(tempLanche.servir());
                errorLabel.setText("");
            } catch (ParseException e) {
                mostrarErro("O preço deve ser um número válido para esta ação (##.## ou ##,##).");
            }
        } else {
            mostrarErro("Preencha os campos (Nome, Preço, Tamanho) ou selecione um lanche.");
        }
    }

    // Método preco (ação do botão "Preço" - renomeado para btnVerPreco no FXML para clareza)
    @FXML
    void verPreco(ActionEvent event) { // Renomeei o método para verPreco para clareza
        if (lancheSelecionado != null) {
            txtResultado.setText(lancheSelecionado.precoLanche());
            errorLabel.setText("");
        } else if (camposPreenchidosParaAcao()) {
            try {
                Lanche tempLanche = new Lanche(
                        txtNomeLanche.getText(),
                        df.parse(txtPrecoLanche.getText().replace(",",".")).doubleValue(),
                        txtTamanhoLanche.getText()
                );
                txtResultado.setText(tempLanche.precoLanche());
                errorLabel.setText("");
            } catch (ParseException e) {
                mostrarErro("O preço deve ser um número válido para esta ação (##.## ou ##,##).");
            }
        } else {
            mostrarErro("Preencha os campos (Nome, Preço, Tamanho) ou selecione um lanche.");
        }
    }


    private boolean validarCampos() {
        String nome = txtNomeLanche.getText();
        String precoStr = txtPrecoLanche.getText();
        String tamanho = txtTamanhoLanche.getText();

        if (nome == null || nome.trim().isEmpty() ||
                precoStr == null || precoStr.trim().isEmpty() ||
                tamanho == null || tamanho.trim().isEmpty()) {
            mostrarErro("Preencha todos os campos (Nome, Preço, Tamanho).");
            return false;
        }
        try {
            df.parse(precoStr.replace(",",".")).doubleValue();
        } catch (ParseException e) {
            mostrarErro("Preço deve ser um número válido (ex: 15.50 ou 15,50).");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    private boolean camposPreenchidosParaAcao() {
        return !(txtNomeLanche.getText().trim().isEmpty() ||
                txtPrecoLanche.getText().trim().isEmpty() ||
                txtTamanhoLanche.getText().trim().isEmpty());
    }

    private void limparCampos() {
        txtNomeLanche.clear();
        txtPrecoLanche.clear();
        txtTamanhoLanche.clear();
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