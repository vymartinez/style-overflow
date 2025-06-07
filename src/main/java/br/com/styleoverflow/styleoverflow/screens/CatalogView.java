package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.WebpToPngConverter;
import br.com.styleoverflow.styleoverflow.classes.Product;
import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Size;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogView {
    private final List<Product> allProducts = new ArrayList<>();
    private final VBox catalogBox = new VBox(10);
    private final ComboBox<Gender> genderFilter = new ComboBox<>();
    private final TextField searchField = new TextField();
    private final Button clearFiltersButton = new Button("Limpar Filtros");
    private final List<Product> cartProducts = new ArrayList<>();

    public CatalogView(Stage stage) {
        // Produtos de exemplo
        allProducts.add(new Product(1,"Camiseta Dev", Size.G, 59.90, Gender.MALE, "Preta",  10, "https://rsv-ink-images-production.s3.sa-east-1.amazonaws.com/images/product_v2/main_image/25e66de93142a7929370acddb96e05c8.webp"));
        allProducts.add(new Product(2,"Cropped Java", Size.G, 69.90,Gender.FEMALE, "Preta",  8, "https://rsv-ink-images-production.s3.sa-east-1.amazonaws.com/images/product_v2/main_image/44a2e2bab92721199672fad138b1cab9.webp"));
        allProducts.add(new Product(3,"Moletom C++", Size.G, 120.00,Gender.MALE, "Preta",  5,"https://rsv-ink-images-production.s3.sa-east-1.amazonaws.com/images/product_v2/main_image/ae0482fc0f3b89db8f88a94f8f738d49.webp"));
        allProducts.add(new Product(4,"Blusa Python", Size.G, 90.00,Gender.FEMALE,  "Preta", 12, "https://rsv-ink-images-production.s3.sa-east-1.amazonaws.com/images/product_v2/main_image/7c5c647f237337c2f62794f302322fa7.webp"));
        clearFiltersButton.setOnAction(e -> limparFiltros(stage));
        clearFiltersButton.setVisible(false);
    }

    public VBox getView(Stage stage) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        // Filtro de pesquisa
        genderFilter.getItems().addAll(Gender.values());
        genderFilter.setPromptText("Filtrar por gênero");
        genderFilter.setOnAction(e -> updateCatalog(stage));

        // Campo de pesquisa
        searchField.setPromptText("Pesquisar produto...");
        searchField.textProperty().addListener((obs, oldVal, newVal) -> updateCatalog(stage));

        // Botão ver carrinho
        Button btnCart = new Button("Ver Carrinho");
        btnCart.setOnAction(e -> new CartView(cartProducts).showCart(stage));

        Button btnProfile = new Button("Perfil");
        btnProfile.setOnAction(e -> stage.getScene().setRoot(UserProfile.showProfile(stage)));

        Button logout = new Button("Logout");
        logout.setOnAction(e-> stage.getScene().setRoot(LoginAndRegister.showLogin(stage)));

        btnCart.getStyleClass().add("btn-primary");
        btnProfile.getStyleClass().add("btn-primary");
        logout.getStyleClass().add("btn-primary");

        HBox topBar = new HBox(10, genderFilter, searchField, clearFiltersButton, btnCart, new Separator(), btnProfile,new Text("                                             "), logout);
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

        boolean filtroAtivo = genderFilter.getValue() != null || !searchField.getText().isEmpty();
        clearFiltersButton.setVisible(filtroAtivo);

        List<Product> filtered = allProducts.stream()
                .filter(p -> {
                    if (genderFilter.getValue() != null && p.getGender() != genderFilter.getValue()) return false;
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
            Label nome = new Label(product.getName());
            Label preco = new Label("R$ " + String.format("%.2f", product.getPrice()));
            Label estoque = new Label("Estoque: " + product.getStock());
            Button btnAddCart = new Button("Adicionar ao Carrinho");
            Button seeDetails = new Button("Ver detalhes");

            btnAddCart.getStyleClass().add("btn-primary");
            seeDetails.getStyleClass().add("btn-primary");

            btnAddCart.setOnAction(e -> cartProducts.add(product));
            seeDetails.setOnAction(e -> stage.getScene().setRoot(ProductDetail.showProduct(stage, product.getName(), product.getPrice(), photoUrl)));
            HBox buttonBox = new HBox(10, btnAddCart, seeDetails);
            info.getChildren().addAll(nome, preco, estoque, buttonBox);

            box.getChildren().addAll(image, info);
        } catch (Exception e) {}

        return box;
    }

    private void limparFiltros(Stage stage) {
        genderFilter.getSelectionModel().clearSelection(); // limpa seleção
        genderFilter.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Gender item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Filtrar por gênero" : item.toString());
            }
        });
        searchField.clear();
        updateCatalog(stage);
    }
}
