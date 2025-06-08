package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Size;
import br.com.styleoverflow.styleoverflow.services.AdminService;
import br.com.styleoverflow.styleoverflow.services.ProductService;
import br.com.styleoverflow.styleoverflow.utils.AlertUtils;
import br.com.styleoverflow.styleoverflow.utils.WebpToPngConverter;
import br.com.styleoverflow.styleoverflow.classes.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;

public class ProductEdit {

    private final AdminService adminService = new AdminService(new ProductService());
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

        ImageView image = null;
        try {
            String photoUrl = WebpToPngConverter.convertWebPToPng(product.getPhotoUrl());
            image = new ImageView(new Image(photoUrl, true));
            image.setFitWidth(100);
            image.setFitHeight(100);
            image.setPreserveRatio(true);
        }catch (Exception e) {}

        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);
        form.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Nome:");
        TextField nameField = new TextField(product.getName());

        Label priceLabel = new Label("Preço:");
        TextField priceField = new TextField(String.valueOf(product.getPrice()));

        Label sizeLabel = new Label("Tamanho:");
        ComboBox<Size> sizeField = new ComboBox<>();
        sizeField.getItems().addAll(Size.values());
        sizeField.setValue(product.getSize());
        sizeField.setPromptText("Selecione o tamanho (P, M, G, etc.)");

        Label genderLabel = new Label("Gênero:");
        ComboBox<String> genderField = new ComboBox<>();
        genderField.getItems().addAll(Arrays.stream(Gender.values()).map(Gender::toPortugueseString).toList());
        genderField.setValue(product.getGender().toString());
        genderField.setPromptText("Selecione o gênero (Masculino ou Feminino)");

        Label colorLabel = new Label("Cor:");
        TextField colorField = new TextField(product.getColor());

        Label photoUrlLabel = new Label("Url da Foto:");
        TextField photoUrlField = new TextField(product.getPhotoUrl().toString());

        Label stockLabel = new Label("Estoque:");
        TextField stockField = new TextField(String.valueOf(product.getStock()));

        nameField.getStyleClass().addAll("max-fit", "min-fit");
        priceField.getStyleClass().addAll("max-fit", "min-fit");
        sizeField.getStyleClass().addAll("max-fit", "min-fit");
        genderField.getStyleClass().addAll("max-fit", "min-fit");
        colorField.getStyleClass().addAll("max-fit", "min-fit");
        stockField.getStyleClass().addAll("max-fit", "min-fit");
        photoUrlField.getStyleClass().addAll("max-fit", "min-fit");

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

        Button backButton = new Button("Voltar");
        Button saveButton = new Button("Salvar Alterações");
        saveButton.getStyleClass().add("btn-primary");
        backButton.getStyleClass().add("btn-primary");

        backButton.setOnAction(e -> {
            stage.getScene().setRoot(new AdminDashboard().getView(stage));
        });

        saveButton.setOnAction(e -> {
            try {
                adminService.patchProduct(
                    nameField.getText(),
                    sizeField.getValue(),
                    genderField.getValue().equals("Masculino") ? Gender.MALE : Gender.FEMALE,
                    colorField.getText(),
                    Integer.parseInt(stockField.getText()),
                    Double.parseDouble(priceField.getText()),
                    photoUrlField.getText(),
                    product.getId()
                );

                stage.getScene().setRoot(new AdminDashboard().getView(stage));
            } catch (Exception exception) {
                AlertUtils.showError(exception.getMessage());
            }
        });

        container.getChildren().addAll(title, image, form, backButton, saveButton);
        return container;
    }
}
