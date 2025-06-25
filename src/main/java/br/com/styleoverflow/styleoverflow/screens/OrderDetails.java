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
import javafx.scene.control.ScrollPane;
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

        VBox root = new VBox(30);
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("root");

        Label title = new Label("Detalhes do Pedido #" + order.getId());
        title.getStyleClass().add("text-primary");

        VBox infoBox = new VBox(10);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.setPadding(new Insets(20));
        infoBox.setMaxWidth(720);
        infoBox.getStyleClass().add("product-card");

        Label dateLabel = new Label("Data do Pedido: " + order.getDate());
        Label statusLabel = new Label("Status: " + order.getStatus());
        Label paymentLabel = new Label("Forma de Pagamento: " + order.getPaymentType());
        Label totalLabel = new Label("Total: R$ " + String.format("%.2f", order.calculateTotal()));

        Label productsTitle = new Label("Itens do pedido:");
        productsTitle.getStyleClass().add("text-primary");

        VBox productsList = new VBox(20);
        productsList.setAlignment(Pos.TOP_CENTER);
        productsList.setPadding(new Insets(10));

        for (ProductOrder productOrder : order.getProducts()) {
            HBox productCard = createProductCard(productOrder);
            productsList.getChildren().add(productCard);
        }

        ScrollPane scrollPane = new ScrollPane(productsList);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxWidth(720);
        scrollPane.setPrefViewportHeight(400);
        scrollPane.setStyle("-fx-background-color: 192841;");

        Button backButton = new Button("Voltar");
        backButton.getStyleClass().add("btn-primary");
        backButton.setOnAction(e -> {
            OrderHistory orderHistory = new OrderHistory(cartProducts);
            stage.getScene().setRoot(orderHistory.getView(stage, user));
        });

        infoBox.getChildren().addAll(dateLabel, statusLabel, paymentLabel, totalLabel, productsTitle, scrollPane);
        root.getChildren().addAll(title, infoBox, backButton);

        return root;
    }

    private static HBox createProductCard(ProductOrder productOrder) {
        HBox productCard = new HBox(15);
        productCard.setAlignment(Pos.CENTER_LEFT);
        productCard.setPadding(new Insets(10));
        productCard.setMaxWidth(680);
        productCard.getStyleClass().add("product-card");

        ImageView imageView = new ImageView();
        try {
            Image productImage = new Image(
                    productOrder.getProduct().getPhotoUrl(),
                    100, 100, true, true, true
            );

            if (productImage.isError()) throw new Exception("Erro ao carregar imagem");

            imageView.setImage(productImage);
        } catch (Exception e) {
            try {
                imageView.setImage(new Image(
                        OrderDetails.class.getResourceAsStream("/images/default-product.png"),
                        100, 100, true, true
                ));
            } catch (Exception ex) {
                imageView.setStyle("-fx-background-color: #192841; -fx-min-width: 100px; -fx-min-height: 100px;");
            }
        }
        imageView.setPreserveRatio(true);

        VBox detailsBox = new VBox(5);
        detailsBox.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label(productOrder.getProduct().getName());
        nameLabel.setStyle("-fx-font-weight: 192841;");

        Label priceLabel = new Label("Preço: R$ " + String.format("%.2f", productOrder.getProduct().getPrice()));
        Label quantityLabel = new Label("Quantidade: " + productOrder.getQuantity());
        Label subtotalLabel = new Label("Subtotal: R$ " + String.format("%.2f", productOrder.calculateSubtotal()));
        subtotalLabel.setStyle("-fx-font-weight: 192841;");

        detailsBox.getChildren().addAll(nameLabel, priceLabel, quantityLabel, subtotalLabel);
        productCard.getChildren().addAll(imageView, detailsBox);
        return productCard;
    }
}
