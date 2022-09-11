package com.sos.game.sos;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SOSController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    // thing 1 to test for hw
    public static int[] selectionSort(int[] arr) {

        for (int i = 0; i < (arr.length - 1); i++) {
            int small = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[small]) {
                    small = j;
                }
            }
            int temp = arr[small];
            arr[small] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }

    // thing 2 to test for hw
    public static String getHelloWorld() {

        return "Hello World!";
    }
}








