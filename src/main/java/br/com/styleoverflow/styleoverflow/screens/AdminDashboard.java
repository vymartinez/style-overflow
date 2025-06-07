package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.Product;
import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Size;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;

public class AdminDashboard {

//    private final ArrayList<Product> products;
//    private final AdminService adminService;
//
//    public AdminDashboard(ArrayList<Product> products) {
//        this.products = adminService.getAllProducts();
//    }

    public BorderPane getView(Stage stage) {
        Label title = new Label("Painel de Administração");
        title.getStyleClass().add("text-primary");

        Button registerButton = new Button("Cadastrar Produto");
        registerButton.setOnAction(e -> {
            stage.getScene().setRoot(new ProductRegister().getView(stage));
        });
        registerButton.getStyleClass().add("btn-primary");

        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("btn-primary");
        logoutButton.setOnAction(e -> {
            stage.getScene().setRoot(LoginAndRegister.showLogin(stage));
        });

        TableView<Product> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Product, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> nameCol = new TableColumn<>("Nome");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, String> sizeCol = new TableColumn<>("Tamanho");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Preço");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, String> colorCol = new TableColumn<>("Cor");
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));

        TableColumn<Product, Integer> stockCol = new TableColumn<>("Em estoque");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Product, Void> actionCol = new TableColumn<>("Ações");
        actionCol.setCellFactory(getActionCellFactory(stage));

        table.getColumns().addAll(idCol, nameCol, sizeCol, priceCol, colorCol, stockCol, actionCol);
        table.setPrefHeight(200);

        table.getItems().addAll(
            new Product(1,"Camiseta Dev", Size.G, 59.90, Gender.MALE, "Preta",  10, "https://rsv-ink-images-production.s3.sa-east-1.amazonaws.com/images/product_v2/main_image/25e66de93142a7929370acddb96e05c8.webp"),
            new Product(2,"Cropped Java", Size.G, 69.90,Gender.FEMALE, "Preta",  8, "https://rsv-ink-images-production.s3.sa-east-1.amazonaws.com/images/product_v2/main_image/44a2e2bab92721199672fad138b1cab9.webp"),
            new Product(3,"Moletom C++", Size.G, 120.00,Gender.MALE, "Preta",  5,"https://rsv-ink-images-production.s3.sa-east-1.amazonaws.com/images/product_v2/main_image/ae0482fc0f3b89db8f88a94f8f738d49.webp"),
            new Product(4,"Blusa Python", Size.G, 90.00,Gender.FEMALE,  "Preta", 12, "https://rsv-ink-images-production.s3.sa-east-1.amazonaws.com/images/product_v2/main_image/7c5c647f237337c2f62794f302322fa7.webp")
        );

        VBox topBox = new VBox(10, title, table);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10));

        VBox bottomBox = new VBox(10, registerButton, logoutButton);
        bottomBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomBox.setPadding(new Insets(10));

        BorderPane layout = new BorderPane();
        layout.setCenter(topBox);
        layout.setBottom(bottomBox);
        layout.setPadding(new Insets(20));

        return layout;
    }

    private Callback<TableColumn<Product, Void>, TableCell<Product, Void>> getActionCellFactory(Stage stage) {
        return param -> new TableCell<>() {
            private final Button editBtn = new Button("Editar");
            private final Button deleteBtn = new Button("Excluir");
            private final HBox pane = new HBox(5, editBtn, deleteBtn);

            {
                editBtn.getStyleClass().add("btn-primary");
                deleteBtn.getStyleClass().add("btn-primary-danger");

                editBtn.setOnAction(e -> {
                    Product product = getTableView().getItems().get(getIndex());
                    stage.getScene().setRoot(new ProductEdit(product).getView(stage));
                });

                deleteBtn.setOnAction(e -> {
                    Product product = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(product);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        };
    }
}
