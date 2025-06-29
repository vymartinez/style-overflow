package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.Product;
import br.com.styleoverflow.styleoverflow.classes.User;
import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Size;
import br.com.styleoverflow.styleoverflow.enums.Role;
import br.com.styleoverflow.styleoverflow.services.AdminService;
import br.com.styleoverflow.styleoverflow.services.ProductService;
import br.com.styleoverflow.styleoverflow.utils.AlertUtils;
import br.com.styleoverflow.styleoverflow.utils.ConfirmationModal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AdminDashboard extends Dashboard {

    private final AdminService adminService = new AdminService(new ProductService());
    private final List<Product> allProducts = ProductService.getAllProducts();
    private final ComboBox<String> genderFilter = new ComboBox<>();
    private final ComboBox<Size> sizeFilter = new ComboBox<>();
    private final TextField searchField = new TextField();
    private final Button clearFiltersButton = new Button("Limpar Filtros");
    private final TableView<Product> table = new TableView<>();
    private User user;

    public AdminDashboard(User user) {this.user = user;}

    @Override
    public BorderPane getView(Stage stage) {

        if (user == null || !user.getRole().equals(Role.ADMIN)) {
            AlertUtils.showError("Acesso negado. Você não tem permissão para acessar o painel de administração.");
            return new BorderPane();
        }

        Label title = new Label("Painel de Administração");
        title.getStyleClass().add("text-primary");

        clearFiltersButton.setOnAction(e -> cleanFilters(stage));
        clearFiltersButton.setVisible(false);

        genderFilter.getItems().addAll(Arrays.stream(Gender.values()).map(Gender::toPortugueseString).toList());
        genderFilter.setPromptText("Filtrar por gênero");
        genderFilter.setOnAction(e -> updateCatalog());

        sizeFilter.getItems().addAll(Size.values());
        sizeFilter.setPromptText("Filtrar por tamanho");
        sizeFilter.setOnAction(e -> updateCatalog());

        searchField.setPromptText("Pesquisar produto...");
        searchField.textProperty().addListener((obs, oldVal, newVal) -> updateCatalog());

        HBox filterBox = new HBox(10, searchField, genderFilter, sizeFilter, clearFiltersButton);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.setPadding(new Insets(10));

        Button registerButton = new Button("Cadastrar Produto");
        registerButton.setOnAction(e -> stage.getScene().setRoot(new ProductRegister(user).getView(stage)));
        registerButton.getStyleClass().add("btn-primary");

        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("btn-primary");
        logoutButton.setOnAction(e -> {
            stage.getScene().setRoot(new LoginAndRegister().showLogin(stage));
        });

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

        TableColumn<Product, Gender> genderCol = new TableColumn<>("Gênero");
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Gender item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toPortugueseString());
            }
        });

        TableColumn<Product, Integer> stockCol = new TableColumn<>("Em estoque");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Product, Void> actionCol = new TableColumn<>("Ações");
        actionCol.setCellFactory(getActionCellFactory(stage));

        table.getColumns().addAll(idCol, nameCol, sizeCol, priceCol, colorCol, genderCol, stockCol, actionCol);
        table.setPrefHeight(400);
        updateCatalog();

        VBox topBox = new VBox(10, title, filterBox, table);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10));

        HBox buttonBox = new HBox(10, registerButton, logoutButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        BorderPane layout = new BorderPane();
        layout.setTop(topBox);
        layout.setBottom(buttonBox);
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
                    stage.getScene().setRoot(new ProductEdit(product, user).getView(stage));
                });

                deleteBtn.setOnAction(e -> {
                    Product product = getTableView().getItems().get(getIndex());

                    try {
                        boolean confirmation = ConfirmationModal.confirmDelete(product.getName());

                        if (confirmation) {
                            adminService.deleteProduct(product.getId(), user);
                            getTableView().getItems().remove(product);
                        }

                    } catch (Exception exception) {
                        AlertUtils.showError(exception.getMessage());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        };
    }

    private void updateCatalog() {
        boolean hasFilter = genderFilter.getValue() != null || !searchField.getText().isEmpty() || sizeFilter.getValue() != null;
        clearFiltersButton.setVisible(hasFilter);

        List<Product> filtered = allProducts.stream()
                .filter(p -> {
                    if (genderFilter.getValue() != null && !genderFilter.getValue().equals(p.getGender().toPortugueseString()))
                        return false;
                    if (sizeFilter.getValue() != null && sizeFilter.getValue() != p.getSize()) return false;
                    return p.getName().toLowerCase().contains(searchField.getText().toLowerCase());
                })
                .collect(Collectors.toList());

        table.getItems().setAll(filtered);
    }

    private void cleanFilters(Stage stage) {
        genderFilter.getSelectionModel().clearSelection();
        genderFilter.setButtonCell(new ListCell<String>() { //
            @Override
            protected void updateItem(String item, boolean empty) { // O item é String aqui
                super.updateItem(item, empty);
                setText("Filtrar por gênero");
            }
        });
        sizeFilter.getSelectionModel().clearSelection();
        sizeFilter.setButtonCell(new ListCell<Size>() { //
            @Override
            protected void updateItem(Size item, boolean empty) { // O item é Size aqui
                super.updateItem(item, empty);
                setText("Filtrar por tamanho");
            }
        });
        searchField.clear();
        updateCatalog();
    }
}
