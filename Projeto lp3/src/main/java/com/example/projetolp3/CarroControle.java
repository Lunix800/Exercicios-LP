package com.example.projetolp3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CarroControle {

    @FXML
    private Button btnAnoCarro;

    @FXML
    private Button btnMarcaCarro;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtAno;

    @FXML
    private TextArea txtResultado;

    @FXML
    void marca(ActionEvent event) {
        Carro testeCarro = new Carro (txtMarca.getText(), txtModelo.getText(), Integer.valueOf(txtAno.getText()));
        txtResultado.setText(testeCarro.marca());
    }

    @FXML
    void ano(ActionEvent event) {
        Carro testeCarro = new Carro (txtMarca.getText(), txtModelo.getText(), Integer.valueOf(txtAno.getText()));
        txtResultado.setText(testeCarro.ano());
    }

    @FXML
    private void VoltarMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FormMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
