module com.example.projetolp3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.projetolp3 to javafx.fxml;
    exports com.example.projetolp3;
    exports com.example.projetolp3.Model;
    opens com.example.projetolp3.Model to javafx.fxml;

}