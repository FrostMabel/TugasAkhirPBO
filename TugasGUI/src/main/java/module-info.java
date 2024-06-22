module com.tugasgui.main.tugasgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tugasgui.main.tugasgui to javafx.fxml;
    exports com.tugasgui.main.tugasgui;
}