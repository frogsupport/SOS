package com.sos.game.sos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.text.*;

import java.io.IOException;

public class SOSApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(SOSApplication.class.getResource("sos-view.fxml"));
        // Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        // set the stage
        stage.setTitle("SOS");

        // create a tile pane
        TilePane tilePane = new TilePane();

        // create a label
        Label labelRadioButton = new Label("Smash that radio button");

        // create radio button names array
        String[] radioButtonNames = new String[]{"Option Alpha", "Option Beta", "Option Gamma"};

        // add labels
        tilePane.getChildren().add(labelRadioButton);

        for (int i = 0; i < radioButtonNames.length; i++) {

            // create radio button
            RadioButton button = new RadioButton(radioButtonNames[i]);

            // add label
            tilePane.getChildren().add(button);
        }

        // checkbox button name array
        String[] checkBoxNames = new String[]{"checkbox i", "checkbox j", "checkbox k"};

        // create a label
        Label labelCheckBox = new Label("Smash that checkbox button");

        // add labels
        tilePane.getChildren().add(labelCheckBox);

        for (int i = 0; i < checkBoxNames.length; i++) {

            // create check box
            CheckBox checkBox = new CheckBox(checkBoxNames[i]);

            // add label
            tilePane.getChildren().add(checkBox);

            // set indeterminate
            checkBox.setIndeterminate(true);
        }

        // add a text label
        Label textLabel = new Label("This is a smashing line of text");

        // add label
        tilePane.getChildren().add(textLabel);

        // create a line
        Line line = new Line(100, 5, 10, 110);

        // add line
        tilePane.getChildren().add(line);

        // create a scene
        Scene scene = new Scene(tilePane, 500, 500);

        // set the scene
        stage.setScene(scene);

        // display the scene
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}