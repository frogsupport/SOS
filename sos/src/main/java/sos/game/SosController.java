package sos.game;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SosController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}