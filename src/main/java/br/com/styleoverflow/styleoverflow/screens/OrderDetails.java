package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.CartProduct;
import br.com.styleoverflow.styleoverflow.classes.Order;
import br.com.styleoverflow.styleoverflow.classes.ProductOrder;
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
    public static Parent getView(Stage stage, Order order, User user, List<CartProduct> cartProducts) {
        if (user == null) {
            AlertUtils.showError("Acesso Negado. Você precisa estar logado para ver os detalhes do pedido.");
            stage.getScene().setRoot(LoginAndRegister.showLogin(stage));
            return new VBox();
        }

        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(40));
        root.getStyleClass().add("root");

        Label title = new Label("Detalhes do Pedido #" + order.getId());
        title.getStyleClass().add("text-primary");

        VBox infoBox = new VBox(15);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.getStyleClass().add("product-card");
        infoBox.setPadding(new Insets(20));
        infoBox.setMaxWidth(600);

        Label dateLabel = new Label("Data do Pedido: " + order.getDate());
        Label statusLabel = new Label("Status: " + order.getStatus());
        Label paymentLabel = new Label("Forma de Pagamento: " + order.getPaymentType());
        Label totalLabel = new Label("Total: R$ " + String.format("%.2f", order.calculateTotal()));

        VBox productsBox = new VBox(10);
        for (ProductOrder productOrder : order.getProducts()) {
            HBox productItem = new HBox(15);
            productItem.setAlignment(Pos.CENTER_LEFT);

            ImageView imageView = new ImageView(new Image(productOrder.getProduct().getPhotoUrl()));
            imageView.setFitWidth(80);
            imageView.setPreserveRatio(true);

            VBox productDetails = new VBox(5);
            productDetails.getChildren().addAll(
                    new Label(productOrder.getProduct().getName()),
                    new Label("Quantidade: " + productOrder.getQuantity()),
                    new Label("Preço unitário: R$ " + String.format("%.2f", productOrder.getProduct().getPrice())),
                    new Label("Subtotal: R$ " + String.format("%.2f", productOrder.calculateSubtotal()))
            );

            productItem.getChildren().addAll(imageView, productDetails);
            productsBox.getChildren().add(productItem);
        }

        infoBox.getChildren().addAll(dateLabel, statusLabel, paymentLabel, totalLabel,
                new Label("Itens do pedido:"), productsBox);

        Button backButton = new Button("Voltar");
        backButton.getStyleClass().add("btn-primary");
        backButton.setOnAction(e -> {
            OrderHistory orderHistory = new OrderHistory(cartProducts);
            stage.getScene().setRoot(orderHistory.getView(stage, user));
        });

        root.getChildren().addAll(title, infoBox, backButton);
        return root;
    }
}