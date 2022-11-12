module com.sos.sos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    exports com.sos.sos;
    opens com.sos.sos to javafx.fxml;
    exports com.sos.models;
    opens com.sos.models to javafx.fxml;
    exports com.sos.providers;
    opens com.sos.providers to javafx.fxml;
}