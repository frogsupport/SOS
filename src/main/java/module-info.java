module com.sos.game.sos {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sos.game.sos to javafx.fxml;
    exports com.sos.game.sos;
}