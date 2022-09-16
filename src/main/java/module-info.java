module com.sos.sos {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sos.sos to javafx.fxml;
    exports com.sos.sos;
}