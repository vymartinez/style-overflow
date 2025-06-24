package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.Product;
import br.com.styleoverflow.styleoverflow.enums.Payment;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class OrderConfirmation {

    public static Parent showConfirmation(Stage stage, List<Product> products) {
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

        Label headerSub = new Label("Subtotal");
        headerSub.setMinWidth(100);
        headerSub.getStyleClass().add("table-header");

        headerRow.getChildren().addAll(headerName, headerUnit, headerSub);
        productList.getChildren().add(headerRow);

        double total = 0;

        for (Product p : products) {
            // Calcular subtotal para cada produto (preço unitário)
            double subtotal = p.getPrice();
            total += subtotal;

            HBox tableRow = new HBox(10);
            tableRow.setAlignment(Pos.CENTER_LEFT);

            Label nameLabel = new Label(p.getName());
            nameLabel.setMinWidth(200);
            nameLabel.getStyleClass().add("label");

            Label sizeLabel = new Label(p.getSize().toString());
            sizeLabel.setMinWidth(80);
            sizeLabel.getStyleClass().add("label");

            Label unitPrice = new Label("R$ " + String.format("%.2f", subtotal));
            unitPrice.setMinWidth(100);
            unitPrice.getStyleClass().add("label");

            tableRow.getChildren().addAll(nameLabel, sizeLabel, unitPrice);
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
            stage.getScene().setRoot(new CatalogView(stage, products).getView(stage));
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