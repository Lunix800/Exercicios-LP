package com.example.projetolp3;

import com.example.projetolp3.Model.Moto;
import com.example.projetolp3.DAO.MotoDAO;
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

public class MotoControle {

    @FXML private TextField txtMarcaMoto;
    @FXML private TextField txtModeloMoto; // Assumindo que o fx:id no FXML será corrigido para txtModeloMoto
    // Se o FXML mantiver txtModeloMarca, mude esta linha para: @FXML private TextField txtModeloMarca;
    @FXML private TextField txtCilindradaMoto;
    @FXML private TextArea txtResultado;
    @FXML private ComboBox<Moto> cbMotos; // Adicionado
    @FXML private Label errorLabel;
    @FXML private Button btnSalvar;        // Adicionado
    @FXML private Button btnDeletarMoto;   // Adicionado
    @FXML private Button btnAcelerarMoto;  // Mantido
    @FXML private Button btnFrearMoto;     // Mantido

    private MotoDAO motoDAO;
    private Moto motoSelecionada;

    @FXML
    public void initialize() {
        try {
            motoDAO = new MotoDAO();
            motoDAO.createTable();
            carregarMotos();
            atualizarInterface();
        } catch (SQLException e) {
            mostrarErro("Erro ao conectar com o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void carregarMotos() {
        try {
            ObservableList<Moto> motos = FXCollections.observableArrayList(motoDAO.getAll());
            cbMotos.setItems(motos);

            cbMotos.setCellFactory(param -> new ListCell<Moto>() {
                @Override
                protected void updateItem(Moto moto, boolean empty) {
                    super.updateItem(moto, empty);
                    if (empty || moto == null) {
                        setText(null);
                    } else {
                        setText(moto.getMarca() + " " + moto.getModelo() + " (" + moto.getCilindrada() + "cc)");
                    }
                }
            });

            cbMotos.setButtonCell(new ListCell<Moto>() {
                @Override
                protected void updateItem(Moto moto, boolean empty) {
                    super.updateItem(moto, empty);
                    if (empty || moto == null) {
                        setText("Selecione uma moto");
                    } else {
                        setText(moto.getMarca() + " " + moto.getModelo());
                    }
                }
            });

            cbMotos.getSelectionModel().selectedItemProperty().addListener(
                    (obs, oldVal, newVal) -> {
                        motoSelecionada = newVal;
                        atualizarInterface();
                    });
        } catch (SQLException e) {
            mostrarErro("Erro ao carregar motos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void atualizarInterface() {
        if (motoSelecionada != null) {
            txtMarcaMoto.setText(motoSelecionada.getMarca());
            // Use o nome correto do TextField aqui, conforme o @FXML e o FXML
            if (txtModeloMoto != null) txtModeloMoto.setText(motoSelecionada.getModelo());
            // else if (txtModeloMarca != null) txtModeloMarca.setText(motoSelecionada.getModelo()); // Se manteve txtModeloMarca
            txtCilindradaMoto.setText(String.valueOf(motoSelecionada.getCilindrada()));
            btnSalvar.setText("Atualizar");
            txtResultado.setText("Editando: " + motoSelecionada.getMarca() + " " + motoSelecionada.getModelo());
        } else {
            limparCampos();
            btnSalvar.setText("Salvar");
            txtResultado.setText("Preencha os dados para cadastrar uma nova moto ou selecione uma para editar.");
        }
    }

    @FXML
    private void salvar(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }
        try {
            int cilindrada = Integer.parseInt(txtCilindradaMoto.getText());
            String marca = txtMarcaMoto.getText();
            // Use o nome correto do TextField aqui
            String modelo = (txtModeloMoto != null) ? txtModeloMoto.getText() : ""; // ou txtModeloMarca.getText()

            if (motoSelecionada == null) { // Criar nova
                Moto novaMoto = new Moto(marca, modelo, cilindrada);
                if (motoDAO.insert(novaMoto)) {
                    mostrarMensagem("Moto cadastrada com sucesso!");
                    carregarMotos();
                    limparCampos();
                } else {
                    mostrarErro("Falha ao cadastrar moto.");
                }
            } else { // Atualizar existente
                motoSelecionada.setMarca(marca);
                motoSelecionada.setModelo(modelo);
                motoSelecionada.setCilindrada(cilindrada);

                if (motoDAO.update(motoSelecionada)) {
                    mostrarMensagem("Moto atualizada com sucesso!");
                    int selectedIndex = cbMotos.getSelectionModel().getSelectedIndex();
                    carregarMotos();
                    cbMotos.getSelectionModel().select(selectedIndex);
                } else {
                    mostrarErro("Falha ao atualizar moto.");
                }
            }
        } catch (NumberFormatException e) {
            mostrarErro("Cilindrada deve ser um valor numérico inteiro.");
        } catch (SQLException e) {
            mostrarErro("Erro no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void deletarMoto(ActionEvent event) {
        if (motoSelecionada != null) {
            try {
                if (motoDAO.delete(motoSelecionada.getId())) {
                    mostrarMensagem("Moto deletada: " + motoSelecionada.getMarca() + " " + motoSelecionada.getModelo());
                    carregarMotos();
                    cbMotos.getSelectionModel().select(null);
                } else {
                    mostrarErro("Falha ao deletar moto.");
                }
            } catch (SQLException e) {
                mostrarErro("Erro ao deletar moto: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            mostrarErro("Selecione uma moto para deletar.");
        }
    }

    @FXML
    void acelerar(ActionEvent event) {
        Moto motoParaAcao = null;
        if (motoSelecionada != null) {
            motoParaAcao = motoSelecionada;
        } else if (camposPreenchidosParaAcao()) {
            try {
                // Use o nome correto do TextField para modelo
                String modelo = (txtModeloMoto != null) ? txtModeloMoto.getText() : "";
                motoParaAcao = new Moto(
                        txtMarcaMoto.getText(),
                        modelo,
                        Integer.parseInt(txtCilindradaMoto.getText())
                );
            } catch (NumberFormatException e) {
                mostrarErro("Cilindrada inválida para esta ação.");
                return;
            }
        }

        if (motoParaAcao != null) {
            txtResultado.setText(motoParaAcao.acelerar());
            errorLabel.setText("");
        } else {
            mostrarErro("Preencha os campos (Marca, Modelo, Cilindrada) ou selecione uma moto.");
        }
    }

    @FXML
    void frear(ActionEvent event) {
        Moto motoParaAcao = null;
        if (motoSelecionada != null) {
            motoParaAcao = motoSelecionada;
        } else if (camposPreenchidosParaAcao()) {
            try {
                // Use o nome correto do TextField para modelo
                String modelo = (txtModeloMoto != null) ? txtModeloMoto.getText() : "";
                motoParaAcao = new Moto(
                        txtMarcaMoto.getText(),
                        modelo,
                        Integer.parseInt(txtCilindradaMoto.getText())
                );
            } catch (NumberFormatException e) {
                mostrarErro("Cilindrada inválida para esta ação.");
                return;
            }
        }

        if (motoParaAcao != null) {
            txtResultado.setText(motoParaAcao.frear());
            errorLabel.setText("");
        } else {
            mostrarErro("Preencha os campos (Marca, Modelo, Cilindrada) ou selecione uma moto.");
        }
    }

    private boolean validarCampos() {
        String marca = txtMarcaMoto.getText();
        // Use o nome correto do TextField para modelo
        String modelo = (txtModeloMoto != null) ? txtModeloMoto.getText() : "";
        String cilindradaStr = txtCilindradaMoto.getText();

        if (marca == null || marca.trim().isEmpty() ||
                modelo == null || modelo.trim().isEmpty() ||
                cilindradaStr == null || cilindradaStr.trim().isEmpty()) {
            mostrarErro("Preencha todos os campos (Marca, Modelo, Cilindrada).");
            return false;
        }
        try {
            Integer.parseInt(cilindradaStr);
        } catch (NumberFormatException e) {
            mostrarErro("Cilindrada deve ser um valor numérico inteiro.");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    private boolean camposPreenchidosParaAcao() {
        // Use o nome correto do TextField para modelo
        String modelo = (txtModeloMoto != null) ? txtModeloMoto.getText() : "";
        return !(txtMarcaMoto.getText().trim().isEmpty() ||
                modelo.trim().isEmpty() ||
                txtCilindradaMoto.getText().trim().isEmpty());
    }

    private void limparCampos() {
        txtMarcaMoto.clear();
        // Use o nome correto do TextField para modelo
        if (txtModeloMoto != null) txtModeloMoto.clear();
        // else if (txtModeloMarca != null) txtModeloMarca.clear();
        txtCilindradaMoto.clear();
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