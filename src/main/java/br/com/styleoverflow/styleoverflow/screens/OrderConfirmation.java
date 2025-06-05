package br.com.styleoverflow.styleoverflow.screens;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrderConfirmation {

    public static Parent showConfirmation(Stage stage, String nomeProduto, double preco) {
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("root");

        Label title = new Label("Pedido Confirmado!");
        title.getStyleClass().add("text-primary");

        Label produto = new Label("Produto: " + nomeProduto);
        produto.getStyleClass().add("label");

        Label precoLabel = new Label("Preço total: R$ " + String.format("%.2f", preco));
        precoLabel.getStyleClass().add("label");

        Button voltarButton = new Button("Voltar");
        voltarButton.getStyleClass().add("btn-primary");

        voltarButton.setOnAction(e -> {
            stage.close(); // ou retorne para tela inicial, se tiver
        });

        VBox content = new VBox(15, title, produto, precoLabel, voltarButton);
        content.setAlignment(Pos.CENTER);
        root.getChildren().add(content);

        return root;
    }
}
