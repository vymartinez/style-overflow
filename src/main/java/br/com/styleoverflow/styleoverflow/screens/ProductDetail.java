package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.CartProduct;
import br.com.styleoverflow.styleoverflow.classes.Product;
import br.com.styleoverflow.styleoverflow.classes.User;
import br.com.styleoverflow.styleoverflow.utils.AlertUtils;
import br.com.styleoverflow.styleoverflow.utils.WebpToPngConverter;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class ProductDetail {

    private User user;
    private Product product;

    public ProductDetail(Product product, User user) {
        this.user = user;
        this.product = product;
    }

    public Parent showProduct(Stage stage) {

        if (this.user == null) {
            AlertUtils.showError("Acesso Negado. Você precisa estar logado para ver os detalhes do produto.");
            stage.getScene().setRoot(new LoginAndRegister().showLogin(stage));
            return new VBox();
        }

        VBox root = new VBox(30);
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("root");

        Label title = new Label("Detalhes do Produto");
        title.getStyleClass().add("text-primary");
        title.setWrapText(true);
        title.setMaxWidth(600);
        title.setAlignment(Pos.CENTER);

        try {
            ImageView productImage = new ImageView(new Image(WebpToPngConverter.convertWebPToPng(product.getPhotoUrl()), true));
            productImage.setFitHeight(200);
            productImage.setPreserveRatio(true);

            Label nomeLabel = new Label(product.getName());
            nomeLabel.getStyleClass().add("text-primary");
            nomeLabel.setWrapText(true);
            nomeLabel.setMaxWidth(600);
            nomeLabel.setAlignment(Pos.CENTER);

            Label sizeLabel = new Label("Tamanho: " + product.getSize().toString());
            Label stockLabel = new Label("Em estoque: " + product.getStock().toString());
            Label precoLabel = new Label("Preço: R$ " + String.format("%.2f", product.getPrice()));
            Label colorLabel = new Label("Cor: " + product.getColor());

            Button voltarButton = new Button("Voltar");
            voltarButton.getStyleClass().add("btn-primary");

            VBox content = new VBox(20,
                    title,
                    productImage,
                    nomeLabel,
                    colorLabel,
                    sizeLabel,
                    stockLabel,
                    precoLabel,
                    voltarButton
            );
            content.setAlignment(Pos.TOP_CENTER);
            content.setMaxWidth(800);

            root.getChildren().add(content);

            voltarButton.setOnAction(e -> {
                stage.getScene().setRoot(new CatalogView(stage, user).getView(stage));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }
}
