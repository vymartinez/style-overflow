package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.CartProduct;
import br.com.styleoverflow.styleoverflow.classes.User;
import br.com.styleoverflow.styleoverflow.utils.AlertUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class OrderDetails {
    public static Parent getView(Stage stage, OrderHistory.Order order, User user, List<CartProduct> cartProducts) {
        if (user == null) {
            AlertUtils.showError("Acesso Negado. Você precisa estar logado para ver os detalhes do pedido.");
            stage.getScene().setRoot(LoginAndRegister.showLogin(stage));
            return new VBox();
        }

        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(40));
        root.getStyleClass().add("root");

        Label title = new Label("Detalhes do Pedido");
        title.getStyleClass().add("text-primary");

        ImageView imageView = new ImageView(new Image(order.imagePath));
        imageView.setFitWidth(160);
        imageView.setPreserveRatio(true);

        VBox infoBox = new VBox(15);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.getStyleClass().add("product-card");
        infoBox.setPadding(new Insets(20));
        infoBox.setMaxWidth(420);

        Label dateLabel = new Label("Data do Pedido: " + order.date);
        Label sizeLabel = new Label("Tamanho: " + order.size);
        Label quantityLabel = new Label("Quantidade: " + order.quantity);
        Label paymentLabel = new Label("Pagamento: " + order.paymentMethod);
        Label genderLabel = new Label("Gênero: " + order.gender);

        dateLabel.getStyleClass().add("label");
        sizeLabel.getStyleClass().add("label");
        quantityLabel.getStyleClass().add("label");
        paymentLabel.getStyleClass().add("label");
        genderLabel.getStyleClass().add("label");

        infoBox.getChildren().addAll(dateLabel, sizeLabel, quantityLabel, paymentLabel, genderLabel);

        Button backButton = new Button("Voltar");
        backButton.getStyleClass().add("btn-primary");
        backButton.setOnAction(e -> {
            OrderHistory orderHistory = new OrderHistory(cartProducts);
            stage.getScene().setRoot(orderHistory.getView(stage, user));
        });
        root.getChildren().addAll(title, imageView, infoBox, backButton);
        return root;
    }
}
