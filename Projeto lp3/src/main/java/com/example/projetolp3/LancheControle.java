package com.example.projetolp3;

import com.example.projetolp3.Model.Lanche;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LancheControle {

    @FXML
    private Button btnProntoLanche;

    @FXML
    private Button btnTamanhoLanche;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField txtNomeLanche;

    @FXML
    private TextField txtPrecoLanche;

    @FXML
    private TextArea txtResultado;

    @FXML
    private TextField txtTamanhoLanche;

    @FXML
    void servir(ActionEvent event) {
        try {
            Lanche testeLanche = new Lanche(
                    txtNomeLanche.getText(),
                    txtPrecoLanche.getText(),
                    Integer.valueOf(txtPrecoLanche.getText())
            );
            txtResultado.setText(testeLanche.servir());
            errorLabel.setText("");
        } catch (NumberFormatException e) {
            errorLabel.setText("Erro: Por favor insira um número válido para o preço!");
        }
    }

    @FXML
    void preco(ActionEvent event) {
        try {
            Lanche testeLanche = new Lanche(
                    txtNomeLanche.getText(),
                    txtPrecoLanche.getText(),
                    Integer.valueOf(txtPrecoLanche.getText())
            );
            txtResultado.setText(testeLanche.preco());
            errorLabel.setText("");
        } catch (NumberFormatException e) {
            errorLabel.setText("Erro: Por favor insira um número válido para o preço!");
        }
    }

    @FXML
    private void VoltarMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FormMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}

