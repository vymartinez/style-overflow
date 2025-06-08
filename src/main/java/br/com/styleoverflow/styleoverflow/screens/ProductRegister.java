package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Size;
import br.com.styleoverflow.styleoverflow.services.AdminService;
import br.com.styleoverflow.styleoverflow.services.ProductService;
import br.com.styleoverflow.styleoverflow.utils.AlertUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;

public class ProductRegister {

    private final AdminService adminService = new AdminService(new ProductService());

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
        ComboBox<Size> sizeField = new ComboBox<>();
        sizeField.getItems().addAll(Size.values());
        sizeField.setPromptText("Selecione o tamanho (P, M, G, etc.)");
        sizeField.getStyleClass().add("max-fit");
        sizeField.getStyleClass().add("min-fit");

        Label genderLabel = new Label("Gênero:");
        ComboBox<String> genderField = new ComboBox<>();
        genderField.getItems().addAll(Arrays.stream(Gender.values()).map(Gender::toPortugueseString).toList());
        genderField.setPromptText("Selecione o gênero (Masculino ou Feminino)");
        genderField.getStyleClass().add("max-fit");
        genderField.getStyleClass().add("min-fit");

        Label colorLabel = new Label("Cor:");
        TextField colorField = new TextField();
        colorField.setPromptText("Digite a cor:");
        colorField.getStyleClass().add("max-fit");
        colorField.getStyleClass().add("min-fit");

        Label photoUrlLabel = new Label("Url da Foto:");
        TextField photoUrlField = new TextField();
        photoUrlField.setPromptText("Digite a url:");
        photoUrlField.getStyleClass().add("max-fit");
        photoUrlField.getStyleClass().add("min-fit");

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
        form.add(photoUrlLabel, 0, 5);
        form.add(photoUrlField, 1, 5);
        form.add(stockLabel, 0, 6);
        form.add(stockField, 1, 6);

        Button backBtn = new Button("Voltar");
        Button registerBtn = new Button("Salvar Produto");
        registerBtn.getStyleClass().add("btn-primary");
        backBtn.getStyleClass().add("btn-primary");

        backBtn.setOnAction(e -> {
            stage.getScene().setRoot(new AdminDashboard().getView(stage));
        });

        registerBtn.setOnAction(e -> {
            try {
                adminService.createProduct(
                    nameField.getText(),
                    sizeField.getValue(),
                    genderField.getValue().equals("Masculino") ? Gender.MALE : Gender.FEMALE,
                    colorField.getText(),
                    Integer.parseInt(stockField.getText()),
                    Double.parseDouble(priceField.getText()),
                    photoUrlField.getText()
                );

                stage.getScene().setRoot(new AdminDashboard().getView(stage));
            } catch (Exception exception) {
                AlertUtils.showError(exception.getMessage());
            }
        });

        container.getChildren().addAll(title, form, backBtn, registerBtn);
        return container;
    }
}
