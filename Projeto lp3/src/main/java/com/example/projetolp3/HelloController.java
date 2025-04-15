package com.example.projetolp3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void btnTESTE() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}