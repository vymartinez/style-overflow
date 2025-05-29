package br.com.styleoverflow.styleoverflow;

import br.com.styleoverflow.styleoverflow.screens.CartView;
import br.com.styleoverflow.styleoverflow.screens.CatalogView;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        CatalogView catalogView = new CatalogView();
        Parent root = catalogView.getView(stage);

        Scene scene = new Scene(root, 960, 600);

        stage.setTitle("StyleOverflow");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
