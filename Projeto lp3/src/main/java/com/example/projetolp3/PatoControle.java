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

public class PatoControle {

    @FXML
    private Button btnNadar;

    @FXML
    private Button btnVoar;

    @FXML
    private TextField txtCorPato;

    @FXML
    private TextField txtPesoPato;

    @FXML
    private TextField txtRacaPato;

    @FXML
    private TextArea txtResultado;

    @FXML
    void nadar(ActionEvent event) { Pato testePato = new Pato (
            txtCorPato.getText(),
            txtRacaPato.getText(),
            Integer.valueOf(txtPesoPato.getText())
    );
        txtResultado.setText(testePato.nadar());

    }

    @FXML
    void voar(ActionEvent event) { Pato testePato = new Pato (
            txtCorPato.getText(),
            txtRacaPato.getText(),
            Integer.valueOf(txtPesoPato.getText())
    );
        txtResultado.setText(testePato.voar());

    }
    @FXML
    private void VoltarMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FormMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
