package com.example.projetolp3;

import com.example.projetolp3.Model.Celular;
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

public class CelularControle {

    @FXML
    private Button btnBateriaCelular;

    @FXML
    private Button btnMarcaCelular;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField txtBateriaCelular;

    @FXML
    private TextField txtMarcaCelular;

    @FXML
    private TextField txtModeloCelular;

    @FXML
    private TextArea txtResultadoCelular;

    @FXML
    void bateria(ActionEvent event) {
        try {
            Celular testeCelular = new Celular(
                    txtMarcaCelular.getText(),
                    txtModeloCelular.getText(),
                    Integer.valueOf(txtBateriaCelular.getText())
            );
            txtResultadoCelular.setText(testeCelular.bateria());
            errorLabel.setText("");

        } catch (NumberFormatException e) {
            errorLabel.setText("Erro: Por favor insira um número inteiro válido!");
        }
    }

    @FXML
    void marca(ActionEvent event) {
        try {
            Celular testeCelular = new Celular(
                    txtMarcaCelular.getText(),
                    txtModeloCelular.getText(),
                    Integer.valueOf(txtBateriaCelular.getText())
            );
            txtResultadoCelular.setText(testeCelular.marca());
            errorLabel.setText("");

        } catch (NumberFormatException e) {
            errorLabel.setText("Erro: Por favor insira um número inteiro válido!");
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

