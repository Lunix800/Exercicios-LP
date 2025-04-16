package com.example.projetolp3;

import com.example.projetolp3.Model.Gato;
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

public class GatoControle {

    @FXML
    private Button btnArranharGato;

    @FXML
    private Button btnMiarGato;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField txtCorGato;

    @FXML
    private TextField txtPesoGato;

    @FXML
    private TextField txtRacaGato;

    @FXML
    private TextArea txtResultado;

    @FXML
    void miar(ActionEvent event) {
        try {
            Gato testeGato = new Gato(
                    txtRacaGato.getText(),
                    txtCorGato.getText(),
                    Integer.valueOf(txtPesoGato.getText())
            );
            txtResultado.setText(testeGato.miar());
            errorLabel.setText("");
        } catch (NumberFormatException e) {
            errorLabel.setText("Erro: Por favor insira um número inteiro válido para o peso!");
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