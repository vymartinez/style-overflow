package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.Order;
import br.com.styleoverflow.styleoverflow.classes.User;
import br.com.styleoverflow.styleoverflow.enums.Status;
import br.com.styleoverflow.styleoverflow.services.OrderService;
import br.com.styleoverflow.styleoverflow.ConnectionFactory;
import br.com.styleoverflow.styleoverflow.utils.AlertUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class OrderHistory {

    private final User user;
    private final OrderService orderService = new OrderService(new ConnectionFactory());
    private final List<Order> orders;

    public OrderHistory(User user) {
        this.user = user;
        this.orders = orderService.getOrdersByCustomerId(user.getId());
    }

    public Parent getView(Stage stage) {
        if (user == null) {
            AlertUtils.showError("Acesso Negado. Você precisa estar logado para ver o histórico de pedidos.");
            stage.getScene().setRoot(new LoginAndRegister().showLogin(stage));
            return new VBox();
        }

        VBox root = new VBox(30);
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("root");

        Label title = new Label("Histórico de Pedidos");
        title.getStyleClass().add("text-primary");

        VBox ordersList = new VBox(20);
        ordersList.setAlignment(Pos.TOP_CENTER);
        ordersList.getStyleClass().add("scroll-container");

            for (Order order : orders) {
                VBox orderCard = new VBox(15);
                orderCard.setAlignment(Pos.CENTER_LEFT);
                orderCard.setPadding(new Insets(15));
                orderCard.getStyleClass().add("product-card");
                orderCard.setMaxWidth(640);

                // Resumo do pedido
                Label orderIdLabel = new Label("Pedido #" + order.getId());
                orderIdLabel.setFont(Font.font(16));

                Label dateLabel = new Label("Data: " + order.getDate());
                Label statusLabel = new Label("Status: " + order.getStatus().toPortgueseString());
                Label paymentLabel = new Label("Pagamento: " + order.getPaymentType().toPortgueseString());
                Label totalLabel = new Label("Total: R$ " + String.format("%.2f", order.calculateTotal()));

                // Botões
                HBox buttonsBox = new HBox(10);
                Button detailButton = new Button("Ver Detalhes");
                detailButton.getStyleClass().add("btn-primary");
                detailButton.setOnAction(e -> {
                    Parent detailView = new OrderDetails(order, user).getView(stage);
                    stage.getScene().setRoot(detailView);
                });

                if (order.getStatus() == Status.PENDING) {
                    Button markDeliveredButton = new Button("Marcar como Entregue");
                    markDeliveredButton.getStyleClass().add("btn-primary-danger");
                    markDeliveredButton.setOnAction(e -> {
                        try {
                            orderService.updateOrder(Status.DELIVERED, order.getId());

                            AlertUtils.showAlert("Sucesso", "Pedido marcado como entregue com sucesso.");
                            stage.getScene().setRoot(new UserProfile(user).showProfile(stage));
                        } catch (Exception exception) {
                            AlertUtils.showError(exception.getMessage());
                        }
                    });
                    buttonsBox.getChildren().add(markDeliveredButton);
                }
                buttonsBox.getChildren().add(detailButton);

                orderCard.getChildren().addAll(
                        orderIdLabel,
                        dateLabel,
                        statusLabel,
                        paymentLabel,
                        totalLabel,
                        buttonsBox
                );

                ordersList.getChildren().add(orderCard);
            }

        ScrollPane scrollPane = new ScrollPane(ordersList);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxWidth(720);
        scrollPane.setPrefViewportHeight(500);
        scrollPane.setStyle("-fx-background-color: transparent;");

        Button backButton = new Button("Voltar");
        backButton.getStyleClass().add("btn-primary");
        backButton.setOnAction(e -> stage.getScene().setRoot(new UserProfile(user).showProfile(stage)));

        root.getChildren().addAll(title, scrollPane, backButton);
        return root;
    }
}