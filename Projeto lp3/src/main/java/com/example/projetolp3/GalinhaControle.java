package com.example.projetolp3;

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

public class GalinhaControle {

    @FXML
    private Button btnPesoGalinha;

    @FXML
    private Button btnCacarejarGalinha;

    @FXML
    private Label lblCorGalinha;

    @FXML
    private Label lblPesoGalinha;

    @FXML
    private Label lblRacaGalinha;

    @FXML
    private TextField txtCorGalinha;

    @FXML
    private TextField txtPesoGalinha;

    @FXML
    private TextField txtRacaGalinha;

    @FXML
    private TextArea txtResultado;

    @FXML
    void cacarejar(ActionEvent event) { Galinha testeGalinha = new Galinha (txtRacaGalinha.getText(), txtCorGalinha.getText(), Double.valueOf(txtPesoGalinha.getText()));
        txtResultado.setText(testeGalinha.cacarejar());

    }

    @FXML
    void peso(ActionEvent event) { Galinha testeGalinha = new Galinha (txtRacaGalinha.getText(), txtCorGalinha.getText(), Double.valueOf(txtPesoGalinha.getText()));
        txtResultado.setText(testeGalinha.peso());

    }
    @FXML
    private void VoltarMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FormMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
