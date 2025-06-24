package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.CartProduct;
import br.com.styleoverflow.styleoverflow.enums.Payment;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrderConfirmation {

    public static Parent showConfirmation(Stage stage, ObservableList<CartProduct> cartProducts) {
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("root");

        Label title = new Label("Confirme seu pedido");
        title.getStyleClass().add("text-primary");

        VBox productList = new VBox(15);
        productList.setAlignment(Pos.CENTER);
        productList.getStyleClass().add("product-list");

        HBox headerRow = new HBox(10);
        headerRow.setAlignment(Pos.CENTER);

        Label headerName = new Label("Produto");
        headerName.setMinWidth(200);
        headerName.getStyleClass().add("table-header");

        Label headerUnit = new Label("Tamanho");
        headerUnit.setMinWidth(100);
        headerUnit.getStyleClass().add("table-header");

        Label headerQty = new Label("Quantidade");
        headerQty.setMinWidth(80);
        headerQty.getStyleClass().add("table-header");

        Label headerSub = new Label("Subtotal");
        headerSub.setMinWidth(100);
        headerSub.getStyleClass().add("table-header");

        headerRow.getChildren().addAll(headerName, headerUnit, headerQty, headerSub);
        productList.getChildren().add(headerRow);

        double total = 0;

        for (CartProduct cartProduct : cartProducts) {
            double subtotal = cartProduct.getSubtotal();
            total += subtotal;

            HBox tableRow = new HBox(10);
            tableRow.setAlignment(Pos.CENTER_LEFT);

            Label nameLabel = new Label(cartProduct.getProduct().getName());
            nameLabel.setMinWidth(200);
            nameLabel.getStyleClass().add("label");

            Label sizeLabel = new Label(cartProduct.getProduct().getSize().toString());
            sizeLabel.setMinWidth(80);
            sizeLabel.getStyleClass().add("label");

            Label qtyLabel = new Label(String.valueOf(cartProduct.getQuantity()));
            qtyLabel.setMinWidth(80);
            qtyLabel.getStyleClass().add("label");

            Label subTotalLabel = new Label("R$ " + String.format("%.2f", subtotal));
            subTotalLabel.setMinWidth(100);
            subTotalLabel.getStyleClass().add("label");

            tableRow.getChildren().addAll(nameLabel, sizeLabel, qtyLabel, subTotalLabel);
            productList.getChildren().add(tableRow);
        }

        productList.setAlignment(Pos.CENTER);
        productList.setFillWidth(true);

        ComboBox<Payment> pagamentoBox = new ComboBox<>();
        pagamentoBox.getItems().addAll(Payment.values());
        pagamentoBox.setValue(Payment.CARD);
        pagamentoBox.getStyleClass().add("combobox");

        Label totalLabel = new Label("Total: R$ " + String.format("%.2f", total));
        totalLabel.getStyleClass().add("label");

        Button confirmarButton = new Button("Confirmar Pedido");
        confirmarButton.getStyleClass().add("btn-primary");

        Button voltarButton = new Button("Voltar");
        voltarButton.getStyleClass().add("btn-primary");

        voltarButton.setOnAction(e -> {
            stage.getScene().setRoot(new CatalogView(stage, cartProducts).getView(stage));
        });

        confirmarButton.setOnAction(e -> {
            Payment metodo = pagamentoBox.getValue();
            System.out.println("Pedido confirmado com pagamento via: " + metodo);
            // Aqui você pode adicionar a lógica para finalizar o pedido
        });

        VBox content = new VBox(20,
                title,
                productList,
                new Label("Método de Pagamento:"),
                pagamentoBox,
                totalLabel,
                confirmarButton,
                voltarButton
        );
        content.setAlignment(Pos.CENTER);
        root.getChildren().add(content);

        return root;
    }
}