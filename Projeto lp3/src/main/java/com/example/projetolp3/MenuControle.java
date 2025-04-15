package com.example.projetolp3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuControle {

    @FXML private Button cachorro;
    @FXML private Button carro;
    @FXML private Button celular;
    @FXML private Button galinha;
    @FXML private Button gato;
    @FXML private Button lanche;
    @FXML private Button livro;
    @FXML private Button moto;
    @FXML private Button pato;
    @FXML private Button vaca;

    private void trocarTela(String nomeFxml, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(nomeFxml));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void abrirTelaCachorro(ActionEvent event) throws IOException {
        trocarTela("FormCachorro.fxml", event);
    }

    @FXML
    private void abrirTelaCarro(ActionEvent event) throws IOException {
        trocarTela("FormCarro.fxml", event);
    }

    @FXML
    private void abrirTelaCelular(ActionEvent event) throws IOException {
        trocarTela("FormCelular.fxml", event);
    }

    @FXML
    private void abrirTelaGalinha(ActionEvent event) throws IOException {
        trocarTela("FormGalinha.fxml", event);
    }

    @FXML
    private void abrirTelaGato(ActionEvent event) throws IOException {
        trocarTela("FormGato.fxml", event);
    }

    @FXML
    private void abrirTelaLanche(ActionEvent event) throws IOException {
        trocarTela("FormLanche.fxml", event);
    }

    @FXML
    private void abrirTelaLivro(ActionEvent event) throws IOException {
        trocarTela("FormLivro.fxml", event);
    }

    @FXML
    private void abrirTelaMoto(ActionEvent event) throws IOException {
        trocarTela("FormMoto.fxml", event);
    }

    @FXML
    private void abrirTelaPato(ActionEvent event) throws IOException {
        trocarTela("FormPato.fxml", event);
    }

    @FXML
    private void abrirTelaVaca(ActionEvent event) throws IOException {
        trocarTela("FormVaca.fxml", event);
    }
}
