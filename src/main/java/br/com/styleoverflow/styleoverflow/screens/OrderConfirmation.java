package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.Product;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class OrderConfirmation {

    public static Parent showConfirmation(Stage stage, List<Product> products) {
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("root");

        Label title = new Label("Pedido Confirmado!");
        title.getStyleClass().add("text-primary");

        products.forEach(product -> {
            Label produto = new Label("Produto: " + product.getName());
            Label precoLabel = new Label("Preço total: R$ " + String.format("%.2f", product.getPrice()));


        });


        Button voltarButton = new Button("Voltar");
        voltarButton.getStyleClass().add("btn-primary");

        voltarButton.setOnAction(e -> {
            stage.getScene().setRoot(new CatalogView(stage).getView(stage));
        });

        VBox content = new VBox(15, title, voltarButton);
        content.setAlignment(Pos.CENTER);
        root.getChildren().add(content);

        return root;
    }
}
