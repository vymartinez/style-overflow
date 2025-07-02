package br.com.styleoverflow.styleoverflow;

import br.com.styleoverflow.styleoverflow.screens.LoginAndRegister;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Parent root = new LoginAndRegister().showLogin(stage);
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(
                getClass().getResource("/css/style.css").toExternalForm()
        );
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Style Overflow");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}