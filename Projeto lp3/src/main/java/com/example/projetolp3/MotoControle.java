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

public class MotoControle {

    @FXML
    private Button btnAcelerarMoto;

    @FXML
    private Button btnFrearMoto;

    @FXML
    private TextField txtCilindradaMoto;

    @FXML
    private TextField txtMarcaMoto;

    @FXML
    private TextField txtModeloMarca;

    @FXML
    private TextArea txtResultado;

    @FXML
    void acelerar(ActionEvent event) { Moto testeMoto = new Moto (
            txtMarcaMoto.getText(),
            txtModeloMarca.getText(),
            Integer.valueOf(txtCilindradaMoto.getText())
    );
        txtResultado.setText(testeMoto.acelerar());

    }

    @FXML
    void frear(ActionEvent event) { Moto testeMoto = new Moto (
            txtMarcaMoto.getText(),
            txtModeloMarca.getText(),
            Integer.valueOf(txtCilindradaMoto.getText())
    );
        txtResultado.setText(testeMoto.frear());

    }
    @FXML
    private void VoltarMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FormMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
