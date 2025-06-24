package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.CartProduct;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CartItemView extends HBox {
    private final CartProduct item;

    public CartItemView(CartProduct item, Runnable onRemove) {
        this.item = item;

        Label nomeLabel = new Label(item.getProduct().getName());
        Label tamanhoLabel = new Label(item.getProduct().getSize().toString());
        Label precoLabel = new Label("R$ " + String.format("%.2f", item.getProduct().getPrice()));

        Button removerButton = new Button("Remover");
        removerButton.setOnAction(e -> onRemove.run());

        nomeLabel.setPrefWidth(200);
        tamanhoLabel.setPrefWidth(100);
        precoLabel.setPrefWidth(120);

        this.setSpacing(20);
        this.setPadding(new Insets(10));
        this.getChildren().addAll(nomeLabel, tamanhoLabel, precoLabel, removerButton);
    }
}
