package br.com.styleoverflow.styleoverflow.screens;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoadingScreen {

    public static Parent getView(Stage stage) {
        StackPane root = new StackPane();
        root.getStyleClass().add("loading-root");

        ProgressIndicator indicator = new ProgressIndicator();
        indicator.setPrefSize(80, 80);

        Label loadingLabel = new Label("Carregando...");
        loadingLabel.getStyleClass().add("loading-text");

        VBox content = new VBox(15, indicator, loadingLabel);
        content.setAlignment(Pos.CENTER);

        root.getChildren().add(content);

        return root;
    }
}
