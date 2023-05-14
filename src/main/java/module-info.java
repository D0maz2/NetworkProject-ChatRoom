module com.example.networkproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.networkproject to javafx.fxml;
    exports com.example.networkproject;
}