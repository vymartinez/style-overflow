package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.ConnectionFactory;
import br.com.styleoverflow.styleoverflow.classes.CartProduct;
import br.com.styleoverflow.styleoverflow.classes.User;
import br.com.styleoverflow.styleoverflow.enums.Payment;
import br.com.styleoverflow.styleoverflow.services.OrderService;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderConfirmation {

    private final User user;
    OrderService orderService = new OrderService();

    public OrderConfirmation(User user) {
        this.user = user;
    }

    public Parent showConfirmation(Stage stage) {

        if (user == null) {
            stage.getScene().setRoot(new LoginAndRegister().showLogin(stage));
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

        final double[] subtotalProdutos = {0.0};

        for (CartProduct cartProduct : user.getCurrentCart().getProducts()) {
            double subtotal = cartProduct.getSubtotal();
            subtotalProdutos[0] += subtotal;

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

        // Adicionar linha visual do frete
        final double FRETE_FIXO = 24.99;

        HBox freteRow = new HBox(10);
        freteRow.setAlignment(Pos.CENTER_LEFT);

        Label freteNameLabel = new Label("Frete");
        freteNameLabel.setMinWidth(200);
        freteNameLabel.getStyleClass().add("label");

        Label freteSizeLabel = new Label("-");
        freteSizeLabel.setMinWidth(80);
        freteSizeLabel.getStyleClass().add("label");

        Label freteQtyLabel = new Label("-");
        freteQtyLabel.setMinWidth(80);
        freteQtyLabel.getStyleClass().add("label");

        Label freteTotalLabel = new Label("R$ " + String.format("%.2f", FRETE_FIXO));
        freteTotalLabel.setMinWidth(100);
        freteTotalLabel.getStyleClass().add("label");

        freteRow.getChildren().addAll(freteNameLabel, freteSizeLabel, freteQtyLabel, freteTotalLabel);
        productList.getChildren().add(freteRow);

        productList.setAlignment(Pos.CENTER);
        productList.setFillWidth(true);

        // Controle de total e descontos
        final double[] totalFinal = {subtotalProdutos[0] + FRETE_FIXO};
        final boolean[] descontoAplicado = {false};
        final boolean[] freteGratisAplicado = {false};

        Label totalLabel = new Label("Total: R$ " + String.format("%.2f", totalFinal[0]));
        totalLabel.getStyleClass().add("label");

        // QR Code (Pix)
        HBox qrBox = new HBox();
        qrBox.setAlignment(Pos.CENTER);

        ComboBox<String> pagamentoBox = new ComboBox<>();
        pagamentoBox.getItems().addAll(Arrays.stream(Payment.values()).map(Payment::toPortgueseString).toList());
        pagamentoBox.setValue(Payment.CARD.toPortgueseString());
        pagamentoBox.getStyleClass().add("combobox");
        pagamentoBox.setOnAction(event -> {
            String selectedPayment = pagamentoBox.getValue();
            qrBox.setVisible(selectedPayment.equals("Pix"));
        });

        // Cupom
        HBox cupomBox = new HBox(10);
        cupomBox.setAlignment(Pos.CENTER);

        TextField cupomField = new TextField();
        cupomField.setPromptText("Digite o cupom");
        cupomField.setMaxWidth(200);

        Button aplicarCupomButton = new Button("Aplicar Cupom");
        aplicarCupomButton.getStyleClass().add("btn-primary");

        Label feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-text-fill: red;");

        aplicarCupomButton.setOnAction(e -> {
            String cupom = cupomField.getText().trim().toLowerCase();

            if (cupom.equals("desconto15")) {
                if (descontoAplicado[0]) {
                    feedbackLabel.setText("Cupom 'desconto15' já aplicado.");
                    feedbackLabel.setStyle("-fx-text-fill: orange;");
                    return;
                }

                double desconto = subtotalProdutos[0] * 0.15;
                totalFinal[0] -= desconto;
                totalLabel.setText("Total: R$ " + String.format("%.2f", totalFinal[0]));
                feedbackLabel.setText("Cupom 'desconto15' aplicado com sucesso!");
                feedbackLabel.setStyle("-fx-text-fill: green;");
                descontoAplicado[0] = true;

            } else if (cupom.equals("fretegratis")) {
                if (freteGratisAplicado[0]) {
                    feedbackLabel.setText("Cupom 'fretegratis' já aplicado.");
                    feedbackLabel.setStyle("-fx-text-fill: orange;");
                    return;
                }

                totalFinal[0] -= FRETE_FIXO;
                totalLabel.setText("Total: R$ " + String.format("%.2f", totalFinal[0]));
                feedbackLabel.setText("Cupom 'fretegratis' aplicado com sucesso!");
                feedbackLabel.setStyle("-fx-text-fill: green;");
                freteGratisAplicado[0] = true;

            } else {
                feedbackLabel.setText("Cupom inválido.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        });

        cupomBox.getChildren().addAll(cupomField, aplicarCupomButton);

        // Botões
        Button confirmarButton = new Button("Confirmar Pedido");
        confirmarButton.getStyleClass().add("btn-primary");

        Button voltarButton = new Button("Voltar");
        voltarButton.getStyleClass().add("btn-primary");

        voltarButton.setOnAction(e -> {
            stage.getScene().setRoot(new CatalogView(stage, user).getView(stage));
        });

        confirmarButton.setOnAction(e -> {
            Payment metodo = pagamentoBox.getValue().equals("Pix") ? Payment.PIX : Payment.CARD;

            try {
                orderService.createOrder(user.getId(), metodo, new ArrayList<>(user.getCurrentCart().getProducts()));
                user.getCurrentCart().clear();
                stage.getScene().setRoot(new CatalogView(stage, user).getView(stage));
            } catch (Exception ex) {
                ex.printStackTrace();
                feedbackLabel.setText("Erro ao finalizar pedido: " + ex.getMessage());
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        });

        VBox content = new VBox(20,
                title,
                productList,
                new Label("Método de Pagamento:"),
                pagamentoBox,
                qrBox,
                cupomBox,
                feedbackLabel,
                totalLabel,
                confirmarButton,
                voltarButton
        );
        content.setAlignment(Pos.CENTER);
        root.getChildren().add(content);

        return root;
    }
}
