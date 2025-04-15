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

public class LivroControle {

    @FXML
    private Button btnFolhearLivro;

    @FXML
    private Button btnLerLivro;

    @FXML
    private TextField txtAutorLivro;

    @FXML
    private TextField txtPaginasLivro;

    @FXML
    private TextArea txtResultado;

    @FXML
    private TextField txtTituloLivro;

    @FXML
    void folhear(ActionEvent event) { Livro testeLivro = new Livro (
            txtTituloLivro.getText(),
            txtAutorLivro.getText(),
            Integer.valueOf(txtPaginasLivro.getText())
    );
        txtResultado.setText(testeLivro.folhear());

    }

    @FXML
    void ler(ActionEvent event) { Livro testeLivro = new Livro (
            txtTituloLivro.getText(),
            txtAutorLivro.getText(),
            Integer.valueOf(txtPaginasLivro.getText())
    );
        txtResultado.setText(testeLivro.ler());

    }
    @FXML
    private void VoltarMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FormMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
