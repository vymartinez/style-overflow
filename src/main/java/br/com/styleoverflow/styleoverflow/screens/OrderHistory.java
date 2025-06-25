package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.CartProduct;
import br.com.styleoverflow.styleoverflow.classes.Order;
import br.com.styleoverflow.styleoverflow.classes.User;
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
    private final List<CartProduct> cartProducts;

    public OrderHistory(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public Parent getView(Stage stage, User user) {
        if (user == null) {
            AlertUtils.showError("Acesso Negado. Você precisa estar logado para ver o histórico de pedidos.");
            stage.getScene().setRoot(LoginAndRegister.showLogin(stage));
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

        try {
            OrderService orderService = new OrderService(new ConnectionFactory());
            List<Order> orders = orderService.getOrdersByCustomerId(user.getId());

            if (orders.isEmpty()) {
                Label noOrders = new Label("Você ainda não fez nenhum pedido.");
                noOrders.getStyleClass().add("label");
                ordersList.getChildren().add(noOrders);
            } else {
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
                    Label statusLabel = new Label("Status: " + order.getStatus());
                    Label paymentLabel = new Label("Pagamento: " + order.getPaymentType());
                    Label totalLabel = new Label("Total: R$ " + String.format("%.2f", order.calculateTotal()));

                    // Botões
                    HBox buttonsBox = new HBox(10);
                    Button detailButton = new Button("Ver Detalhes");
                    detailButton.getStyleClass().add("btn-primary");
                    detailButton.setOnAction(e -> {
                        Parent detailView = OrderDetails.getView(stage, order, user, cartProducts);
                        stage.getScene().setRoot(detailView);
                    });

                    if (order.getStatus() == br.com.styleoverflow.styleoverflow.enums.Status.PENDING) {
                        Button markDeliveredButton = new Button("Marcar como Entregue");
                        markDeliveredButton.getStyleClass().add("btn-primary-danger");
                        markDeliveredButton.setOnAction(e -> {
                            orderService.updateOrder(br.com.styleoverflow.styleoverflow.enums.Status.DELIVERED, order.getId());
                            statusLabel.setText("Status: DELIVERED");
                            buttonsBox.getChildren().remove(markDeliveredButton);
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
            }
        } catch (Exception e) {
            AlertUtils.showError("Erro ao carregar histórico de pedidos: " + e.getMessage());
        }

        ScrollPane scrollPane = new ScrollPane(ordersList);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxWidth(720);
        scrollPane.setPrefViewportHeight(500);
        scrollPane.setStyle("-fx-background-color: transparent;");

        Button backButton = new Button("Voltar");
        backButton.getStyleClass().add("btn-primary");
        backButton.setOnAction(e -> stage.getScene().setRoot(UserProfile.showProfile(stage, cartProducts, user)));

        root.getChildren().addAll(title, scrollPane, backButton);
        return root;
    }
}