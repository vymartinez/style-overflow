package br.com.styleoverflow.styleoverflow.screens;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ProductDetail {

    public static Parent showProduct(Stage stage, String nome, double preco, String imagemUrl) {
        VBox root = new VBox(30);
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("root");

        Label title = new Label("Detalhes do Produto");
        title.getStyleClass().add("text-primary");
        title.setWrapText(true);
        title.setMaxWidth(600);
        title.setAlignment(Pos.CENTER);

        ImageView productImage = new ImageView(new Image(imagemUrl));
        productImage.setFitHeight(200);
        productImage.setPreserveRatio(true);

        Label nomeLabel = new Label(nome);
        nomeLabel.getStyleClass().add("text-primary");
        nomeLabel.setWrapText(true);
        nomeLabel.setMaxWidth(600);
        nomeLabel.setAlignment(Pos.CENTER);

        Label precoLabel = new Label("Preço: R$ " + String.format("%.2f", preco));
        precoLabel.getStyleClass().add("label");

        Button voltarButton = new Button("Voltar");
        voltarButton.getStyleClass().add("btn-primary");

        VBox content = new VBox(20,
                title,
                productImage,
                nomeLabel,
                precoLabel,
                voltarButton
        );
        content.setAlignment(Pos.TOP_CENTER);
        content.setMaxWidth(800);

        root.getChildren().add(content);

        voltarButton.setOnAction(e -> {
            stage.getScene().setRoot(new CatalogView(stage).getView(stage));
        });

        return root;
    }
}
