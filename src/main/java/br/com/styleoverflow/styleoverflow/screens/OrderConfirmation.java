package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.Product;
import br.com.styleoverflow.styleoverflow.classes.User;
import br.com.styleoverflow.styleoverflow.enums.Payment;
import br.com.styleoverflow.styleoverflow.utils.AlertUtils;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class OrderConfirmation {

    public static Parent showConfirmation(Stage stage, List<Product> products, User user) {

        if (user == null) {
            AlertUtils.showError("Acesso Negado. Você precisa estar logado para ver o carrinho.");
            stage.getScene().setRoot(LoginAndRegister.showLogin(stage));
            return new VBox();
        }

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

        Label headerUnit = new Label("Preço Unitário");
        headerUnit.setMinWidth(100);
        headerUnit.getStyleClass().add("table-header");

        Label headerSub = new Label("Subtotal");
        headerSub.setMinWidth(100);
        headerSub.getStyleClass().add("table-header");

        headerRow.getChildren().addAll(headerName, headerUnit, headerSub);
        productList.getChildren().add(headerRow);

        double total = 0;

        for (Product p : products) {
            //int qtd = p.getQuantidade();
            int qtd = 1;
            double unit = p.getPrice();
            double subtotal = qtd * unit;
            total += subtotal;

            HBox tableRow = new HBox(10);
            tableRow.setAlignment(Pos.CENTER_LEFT);

            Label qtdName = new Label(qtd + "x " + p.getName());
            qtdName.setMinWidth(200);
            qtdName.getStyleClass().add("label");

            Label unitPrice = new Label("R$ " + String.format("%.2f", unit));
            unitPrice.setMinWidth(100);
            unitPrice.getStyleClass().add("label");

            Label subTotal = new Label("R$ " + String.format("%.2f", subtotal));
            subTotal.setMinWidth(100);
            subTotal.getStyleClass().add("label");

            tableRow.getChildren().addAll(qtdName, unitPrice, subTotal);

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
            stage.getScene().setRoot(new CatalogView(stage, user).getView(stage));
        });

        confirmarButton.setOnAction(e -> {
            Payment metodo = pagamentoBox.getValue();
            System.out.println("Pedido confirmado com pagamento via: " + metodo);
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
