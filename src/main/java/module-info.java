module com.example.libmswgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.libmswgui to javafx.fxml;
    exports com.example.libmswgui;
}