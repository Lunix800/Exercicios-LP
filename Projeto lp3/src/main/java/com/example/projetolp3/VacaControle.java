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

public class VacaControle {

    @FXML
    private Button btnMugirVaca;

    @FXML
    private Button txtComerVaca;

    @FXML
    private TextField txtCorVaca;

    @FXML
    private TextField txtPesoVaca;

    @FXML
    private TextField txtRacaVaca;

    @FXML
    private TextArea txtResultado;

    @FXML
    void comer(ActionEvent event) { Vaca testeVaca = new Vaca (
            txtRacaVaca.getText(),
            txtCorVaca.getText(),
            Integer.valueOf(txtPesoVaca.getText())
    );
        txtResultado.setText(testeVaca.comer());


    }

    @FXML
    void mugir(ActionEvent event) { Vaca testeVaca = new Vaca (
            txtRacaVaca.getText(),
            txtCorVaca.getText(),
            Integer.valueOf(txtPesoVaca.getText())
    );
        txtResultado.setText(testeVaca.mugir());

    }
    @FXML
    private void VoltarMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FormMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
