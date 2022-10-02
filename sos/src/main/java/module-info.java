module sos.sos {
    requires javafx.controls;
    requires javafx.fxml;


    opens sos.game to javafx.fxml;
    exports sos.game;
    exports sos.models;
    opens sos.models to javafx.fxml;
}