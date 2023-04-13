module com.example.mazegen {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mazegen to javafx.fxml;
    exports com.example.mazegen;
}