package com.example.projetolp3;

import com.example.projetolp3.Model.Cachorro;
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

public class CachorroControle {

    @FXML
    private Button btnNomeCachorro;

    @FXML
    private Button btnMenu;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField txtIdade;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtRaca;

    @FXML
    private TextArea txtResultado;

    @FXML
    void nome(ActionEvent event) {
        try {
            Cachorro testeCachorro = new Cachorro(
                    txtNome.getText(),
                    txtRaca.getText(),
                    Integer.valueOf(txtIdade.getText())
            );
            txtResultado.setText(testeCachorro.nome());
            errorLabel.setText("");
        } catch (NumberFormatException e) {
            errorLabel.setText("Erro: Por favor insira um número inteiro válido para a idade!");
        }
    }

    @FXML
    void latir(ActionEvent event) {
        try {
            Cachorro testeCachorro = new Cachorro(
                    txtNome.getText(),
                    txtRaca.getText(),
                    Integer.valueOf(txtIdade.getText())
            );
            txtResultado.setText(testeCachorro.latir());
            errorLabel.setText("");
        } catch (NumberFormatException e) {
            errorLabel.setText("Erro: Por favor insira um número inteiro válido para a idade!");
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


