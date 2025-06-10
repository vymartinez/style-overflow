package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.ConnectionFactory;
import br.com.styleoverflow.styleoverflow.enums.Size;
import br.com.styleoverflow.styleoverflow.services.ProductService;
import br.com.styleoverflow.styleoverflow.utils.WebpToPngConverter;
import br.com.styleoverflow.styleoverflow.classes.Product;
import br.com.styleoverflow.styleoverflow.enums.Gender;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.*;
import java.util.stream.Collectors;

public class CatalogView {
    private final List<Product> allProducts = ProductService.getAllProducts();
    private final VBox catalogBox = new VBox(10);
    private final ComboBox<String> genderFilter = new ComboBox<>();
    private final ComboBox<Size> sizeFilter = new ComboBox<>();
    private final TextField searchField = new TextField();
    private final Button clearFiltersButton = new Button("Limpar Filtros");
    private final List<Product> cartProducts = new ArrayList<>();

    public CatalogView(Stage stage) {
       clearFiltersButton.setOnAction(e -> limparFiltros(stage));
        clearFiltersButton.setVisible(false);
    }

    public VBox getView(Stage stage) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        genderFilter.getItems().addAll(Arrays.stream(Gender.values()).map(Gender::toPortugueseString).toList());
        genderFilter.setPromptText("Filtrar por gênero");
        genderFilter.setOnAction(e -> updateCatalog(stage));

        sizeFilter.getItems().addAll(Size.values());
        sizeFilter.setPromptText("Filtrar por tamanho");
        sizeFilter.setOnAction(e -> updateCatalog(stage));

        searchField.setPromptText("Pesquisar produto...");
        searchField.textProperty().addListener((obs, oldVal, newVal) -> updateCatalog(stage));

        Button btnCart = new Button("Ver Carrinho");
        btnCart.setOnAction(e -> new CartView(cartProducts).showCart(stage));

        Button btnProfile = new Button("Perfil");
        btnProfile.setOnAction(e -> stage.getScene().setRoot(UserProfile.showProfile(stage)));

        Button logout = new Button("Logout");
        logout.setOnAction(e-> stage.getScene().setRoot(LoginAndRegister.showLogin(stage)));

        btnCart.getStyleClass().add("btn-primary");
        btnProfile.getStyleClass().add("btn-primary");
        logout.getStyleClass().add("btn-primary");

        HBox topBar = new HBox(10, genderFilter, sizeFilter, searchField, clearFiltersButton, btnCart, new Separator(), btnProfile,new Text("                                             "), logout);
        topBar.setAlignment(Pos.CENTER_LEFT);


        catalogBox.setPadding(new Insets(10));
        updateCatalog(stage);
        ScrollPane scrollPane = new ScrollPane(catalogBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;");

        scrollPane.prefHeightProperty().bind(stage.heightProperty().multiply(0.5));

        root.getChildren().addAll(topBar, new Separator(), scrollPane);
        return root;
    }

    private void updateCatalog(Stage stage) {
        catalogBox.getChildren().clear();

        boolean filtroAtivo = genderFilter.getValue() != null || !searchField.getText().isEmpty() || sizeFilter.getValue() != null;
        clearFiltersButton.setVisible(filtroAtivo);

        List<Product> filtered = allProducts.stream()
                .filter(p -> {
                    if (genderFilter.getValue() != null && genderFilter.getValue() != p.getGender().toPortugueseString()) return false;
                    if (sizeFilter.getValue() != null && sizeFilter.getValue() != p.getSize()) return false;
                    return p.getName().toLowerCase().contains(searchField.getText().toLowerCase());
                })
                .collect(Collectors.toList());

        for (Product product : filtered) {
            catalogBox.getChildren().add(createProductCard(product, stage));
        }
    }

    private HBox createProductCard(Product product, Stage stage) {
        HBox box = new HBox(15);
        box.setPadding(new Insets(10));
        box.getStyleClass().add("product-card");

        try {
            String photoUrl = WebpToPngConverter.convertWebPToPng(product.getPhotoUrl());
            ImageView image = new ImageView(new Image(photoUrl, true));
            image.setFitWidth(100);
            image.setFitHeight(100);
            VBox info = new VBox(5);
            Label nome = new Label(product.getName() + " (" + product.getSize() + ")");
            Label preco = new Label("R$ " + String.format("%.2f", product.getPrice()));
            Label estoque = new Label("Estoque: " + product.getStock());
            Button btnAddCart = new Button("Adicionar ao Carrinho");
            Button seeDetails = new Button("Ver detalhes");

            btnAddCart.getStyleClass().add("btn-primary");
            seeDetails.getStyleClass().add("btn-primary");

            btnAddCart.setOnAction(e -> cartProducts.add(product));
            seeDetails.setOnAction(e -> stage.getScene().setRoot(ProductDetail.showProduct(stage, product)));
            HBox buttonBox = new HBox(10, btnAddCart, seeDetails);
            info.getChildren().addAll(nome, preco, estoque, buttonBox);

            box.getChildren().addAll(image, info);
        } catch (Exception e) {}

        return box;
    }

    private void limparFiltros(Stage stage) {
        genderFilter.getSelectionModel().clearSelection();
        genderFilter.setButtonCell(new ListCell() {
            protected void updateItem(Gender item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Filtrar por gênero" : item.toString());
            }
        });
        searchField.clear();
        updateCatalog(stage);
    }
}
