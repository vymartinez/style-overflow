package br.com.styleoverflow.styleoverflow;

import br.com.styleoverflow.styleoverflow.screens.AdminDashboard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        AdminDashboard dashboard = new AdminDashboard();
        Scene scene = new Scene(dashboard.getView(), 960, 600);
        scene.getStylesheets().addAll(
                BootstrapFX.bootstrapFXStylesheet()
        );

        stage.setTitle("Admin Dashboard - Style Overflow");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}