package br.com.styleoverflow.styleoverflow.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtils {
    public static void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Ocorreu um erro");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
