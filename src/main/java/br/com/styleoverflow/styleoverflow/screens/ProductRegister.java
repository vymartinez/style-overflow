package br.com.styleoverflow.styleoverflow.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProductRegister {
    public VBox getView(Stage stage) {
        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(20));

        Label title = new Label("Cadastrar Novo Produto");
        title.getStyleClass().add("text-primary");

        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);
        form.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Nome:");
        TextField nameField = new TextField();
        nameField.setPromptText("Digite o nome do produto");
        nameField.getStyleClass().add("max-fit");
        nameField.getStyleClass().add("min-fit");

        Label priceLabel = new Label("Preço:");
        TextField priceField = new TextField();
        priceField.setPromptText("Digite o preço");
        priceField.getStyleClass().add("max-fit");
        priceField.getStyleClass().add("min-fit");

        Label sizeLabel = new Label("Tamanhos:");
        TextField sizeField = new TextField();
        sizeField.setPromptText("Digite o tamanho (P, M, G, etc.)");
        sizeField.getStyleClass().add("max-fit");
        sizeField.getStyleClass().add("min-fit");

        Label genderLabel = new Label("Gênero:");
        TextField genderField = new TextField();
        genderField.setPromptText("Digite o gênero (Masculino ou Feminino)");
        genderField.getStyleClass().add("max-fit");
        genderField.getStyleClass().add("min-fit");

        Label colorLabel = new Label("Cor:");
        TextField colorField = new TextField();
        colorField.setPromptText("Digite a cor:");
        colorField.getStyleClass().add("max-fit");
        colorField.getStyleClass().add("min-fit");

        Label stockLabel = new Label("Estoque:");
        TextField stockField = new TextField();
        stockField.setPromptText("Digite a quantidade em estoque:");
        stockField.getStyleClass().add("max-fit");
        stockField.getStyleClass().add("min-fit");

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

        Button backBtn = new Button("Voltar");
        Button registerBtn = new Button("Salvar Produto");
        registerBtn.getStyleClass().add("btn-primary");
        backBtn.getStyleClass().add("btn-primary");

        backBtn.setOnAction(e -> {
            stage.getScene().setRoot(new AdminDashboard().getView(stage));
        });

        container.getChildren().addAll(title, form, backBtn, registerBtn);
        return container;
    }
}
