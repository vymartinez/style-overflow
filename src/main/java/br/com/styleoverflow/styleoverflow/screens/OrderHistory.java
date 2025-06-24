package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class OrderHistory {

    private final List<Product> cartProducts;

    public OrderHistory(List<Product> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public Parent getView(Stage stage) {
        VBox root = new VBox(30);
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("root");

        Label title = new Label("Histórico de Pedidos");
        title.getStyleClass().add("text-primary");

        VBox ordersList = new VBox(20);
        ordersList.setAlignment(Pos.TOP_CENTER);
        ordersList.getStyleClass().add("scroll-container");

        List<Order> orders = List.of(
                new Order("Cropped Java", "data:image/jpeg;base64,...", "DELIVERED", "2024-05-15", "GG", 1, "Boleto", "Masculino"),
                new Order("Moletom C++", "data:image/jpeg;base64,...", "DELIVERED", "2024-05-25", "G", 2, "Cartão de Crédito", "Unissex"),
                new Order("Camiseta Dev", "data:image/jpeg;base64,...", "PENDING", "2024-06-01", "M", 1, "PIX", "Masculino")
        );

        for (Order order : orders) {
            VBox card = new VBox(10);
            card.setAlignment(Pos.CENTER_LEFT);
            card.setPadding(new Insets(15));
            card.getStyleClass().add("product-card");
            card.setMaxWidth(640);

            HBox content = new HBox(20);
            content.setAlignment(Pos.CENTER_LEFT);

            ImageView image = new ImageView(new Image(order.imagePath));
            image.setFitHeight(100);
            image.setFitWidth(100);

            VBox details = new VBox(5);
            Label name = new Label(order.productName);
            name.setFont(Font.font(18));
            name.getStyleClass().add("label");

            Label statusLabel = new Label("Status: " + order.status);
            statusLabel.getStyleClass().add("label");

            Button detailButton = new Button("Ver Detalhes");
            detailButton.getStyleClass().add("btn-primary");
            detailButton.setOnAction(e -> {
                Parent detailView = OrderDetails.getView(stage, order);
                stage.getScene().setRoot(detailView);
            });

            details.getChildren().addAll(name, statusLabel, detailButton);

            if (order.status.equalsIgnoreCase("PENDING")) {
                Button markDeliveredButton = new Button("Marcar como Entregue");
                markDeliveredButton.getStyleClass().add("btn-primary-danger");
                markDeliveredButton.setOnAction(e -> {
                    order.status = "DELIVERED";
                    statusLabel.setText("Status: DELIVERED");
                    details.getChildren().remove(markDeliveredButton);
                });
                details.getChildren().add(markDeliveredButton);
            }

            content.getChildren().addAll(image, details);
            card.getChildren().add(content);
            ordersList.getChildren().add(card);
        }

        ScrollPane scrollPane = new ScrollPane(ordersList);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxWidth(720);
        scrollPane.setPrefViewportHeight(500);
        scrollPane.setStyle("-fx-background-color: transparent;");

        Button backButton = new Button("Voltar");
        backButton.getStyleClass().add("btn-primary");
        backButton.setOnAction(e -> stage.getScene().setRoot(UserProfile.showProfile(stage, cartProducts)));

        VBox content = new VBox(20, title, scrollPane, backButton);
        content.setAlignment(Pos.TOP_CENTER);

        root.getChildren().add(content);

        return root;
    }

    public static class Order {
        String productName;
        String imagePath;
        String status;
        String date;
        String size;
        int quantity;
        String paymentMethod;
        String gender;

        public Order(String productName, String imagePath, String status, String date,
                     String size, int quantity, String paymentMethod, String gender) {
            this.productName = productName;
            this.imagePath = imagePath;
            this.status = status;
            this.date = date;
            this.size = size;
            this.quantity = quantity;
            this.paymentMethod = paymentMethod;
            this.gender = gender;
        }
    }
}