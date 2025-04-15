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

public class GatoControle {

    @FXML
    private Button btnArranharGato;

    @FXML
    private Button btnMiarGato;

    @FXML
    private TextField txtCorGato;

    @FXML
    private TextField txtPesoGato;

    @FXML
    private TextField txtRacaGato;

    @FXML
    private TextArea txtResultado;

    @FXML
    void arranhar(ActionEvent event) { Gato testeGato = new Gato (
            txtRacaGato.getText(),
            txtCorGato.getText(),
            Integer.valueOf(txtPesoGato.getText())
    );
        txtResultado.setText(testeGato.arranhar());

    }

    @FXML
    void miar(ActionEvent event) { Gato testeGato = new Gato (
            txtRacaGato.getText(),
            txtCorGato.getText(),
            Integer.valueOf(txtPesoGato.getText())
    );
        txtResultado.setText(testeGato.miar());

    }
    @FXML
    private void VoltarMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FormMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
