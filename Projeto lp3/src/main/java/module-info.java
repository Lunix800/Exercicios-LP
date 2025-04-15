module com.example.projetolp3 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.projetolp3 to javafx.fxml;
    exports com.example.projetolp3;

}