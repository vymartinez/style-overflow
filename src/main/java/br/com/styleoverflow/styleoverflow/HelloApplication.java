package br.com.styleoverflow.styleoverflow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import br.com.styleoverflow.styleoverflow.screens.UserProfile;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        UserProfile userProfile = new UserProfile();
        Scene scene = new Scene(userProfile.getUserProfile(stage), 800, 600);
        stage.setScene(scene);
        stage.setTitle("Perfil do Usuário");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}