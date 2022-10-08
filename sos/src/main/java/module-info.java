module com.sos.sos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    exports com.sos;
    opens com.sos to javafx.fxml;
}