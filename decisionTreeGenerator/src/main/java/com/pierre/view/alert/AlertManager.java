package com.pierre.view.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Auxiliary class to generate warning and error messages.
 */
public class AlertManager {

    public static void errorAlert(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("icons/error.png"));
        alert.showAndWait();
    }
}
