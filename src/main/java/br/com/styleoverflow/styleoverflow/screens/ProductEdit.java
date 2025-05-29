package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProductEdit {

    private final Product product;

    public ProductEdit(Product product) {
        this.product = product;
    }

    public VBox getView(Stage stage) {
        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(20));

        Label title = new Label("Editar Produto");
        title.getStyleClass().add("text-primary");

        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);
        form.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Nome:");
        TextField nameField = new TextField(product.getName());

        Label priceLabel = new Label("Preço:");
        TextField priceField = new TextField(String.valueOf(product.getPrice()));

        Label sizeLabel = new Label("Tamanhos:");
        TextField sizeField = new TextField(product.getSize().toString());

        Label genderLabel = new Label("Gênero:");
        TextField genderField = new TextField(product.getGender().toString());

        Label colorLabel = new Label("Cor:");
        TextField colorField = new TextField(product.getColor());

        Label stockLabel = new Label("Estoque:");
        TextField stockField = new TextField(String.valueOf(product.getStock()));

        nameField.getStyleClass().addAll("max-fit", "min-fit");
        priceField.getStyleClass().addAll("max-fit", "min-fit");
        sizeField.getStyleClass().addAll("max-fit", "min-fit");
        genderField.getStyleClass().addAll("max-fit", "min-fit");
        colorField.getStyleClass().addAll("max-fit", "min-fit");
        stockField.getStyleClass().addAll("max-fit", "min-fit");

        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);
        form.add(priceLabel, 0, 1);
        form.add(priceField, 1, 1);
        form.add(sizeLabel, 0, 2);
        form.add(sizeField, 1, 2);
        form.add(genderLabel, 0, 3);
        form.add(genderField, 1, 3);
        form.add(colorLabel, 0, 4);
        form.add(colorField, 1, 4);
        form.add(stockLabel, 0, 5);
        form.add(stockField, 1, 5);

        Button backButton = new Button("Voltar");
        Button saveButton = new Button("Salvar Alterações");
        saveButton.getStyleClass().add("btn-primary");
        backButton.getStyleClass().add("btn-primary");

        backButton.setOnAction(e -> {
            stage.getScene().setRoot(new AdminDashboard().getView(stage));
        });

        // Aqui você pode adicionar lógica de atualização do produto no banco/lista

        container.getChildren().addAll(title, form, backButton, saveButton);
        return container;
    }
}
