package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.CartProduct;
import br.com.styleoverflow.styleoverflow.classes.Product;
import br.com.styleoverflow.styleoverflow.enums.Size;
import br.com.styleoverflow.styleoverflow.classes.User;
import br.com.styleoverflow.styleoverflow.utils.AlertUtils;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CartView {

    private final TableView<CartProduct> tabela;
    private final Label totalLabel;
    private User user;

    public CartView(User user) {
        tabela = new TableView<>();
        totalLabel = new Label(String.format("Total: R$ %.2f", user.getCurrentCart().calculateTotal()));
        this.user = user;

    }

    public Parent getView(Stage currentStage) {

        if (user == null) {
            AlertUtils.showError("Acesso Negado. Você precisa estar logado para ver o carrinho.");
            currentStage.getScene().setRoot(new LoginAndRegister().showLogin(currentStage));
            return new VBox();
        }

        TableColumn<CartProduct, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cellData -> {
            Product product = cellData.getValue().getProduct();
            return new SimpleStringProperty(product.getName());
        });

        TableColumn<CartProduct, Size> colTamanho = new TableColumn<>("Tamanho");
        colTamanho.setCellValueFactory(cellData -> {
            Product product = cellData.getValue().getProduct();
            return new SimpleObjectProperty<>(product.getSize());
        });

        TableColumn<CartProduct, Double> colPrecoUnit = new TableColumn<>("Preço Unit.");
        colPrecoUnit.setCellValueFactory(cellData -> {
            Product product = cellData.getValue().getProduct();
            return new SimpleDoubleProperty(product.getPrice()).asObject();
        });

        TableColumn<CartProduct, Integer> colQuantidade = new TableColumn<>("Quantidade");
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQuantidade.setCellFactory(column -> new TableCell<CartProduct, Integer>() {
            private final Spinner<Integer> spinner = new Spinner<>();

            {
                spinner.setEditable(true);
                spinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);

                spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                    if (getTableRow() != null && getTableRow().getItem() != null) {
                        CartProduct item = getTableRow().getItem();
                        item.setQuantity(newValue);
                        atualizarTotal();
                        getTableView().refresh();
                    }
                });
            }

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    CartProduct cartProduct = getTableRow().getItem();
                    int maxStock = cartProduct.getProduct().getStock();
                    int currentQty = cartProduct.getQuantity();

                    SpinnerValueFactory<Integer> valueFactory =
                            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxStock, currentQty);
                    spinner.setValueFactory(valueFactory);

                    setGraphic(spinner);
                }
            }
        });


        TableColumn<CartProduct, Double> colSubtotal = new TableColumn<>("Subtotal");
        colSubtotal.setCellValueFactory(cellData -> {
            CartProduct cartProduct = cellData.getValue();
            return new SimpleDoubleProperty(cartProduct.getSubtotal()).asObject();
        });

        tabela.getColumns().addAll(colNome, colTamanho, colPrecoUnit, colQuantidade, colSubtotal);
        tabela.setItems(FXCollections.observableArrayList(user.getCurrentCart().getProducts()));
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Botões (agora sem os botões de aumentar/diminuir)
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> voltar(currentStage));

        Button btnRemover = new Button("Remover Selecionado");
        btnRemover.setOnAction(e -> removerSelecionado());

        Button btnConfirmar = new Button("Confirmar compra");
        btnConfirmar.setOnAction(e -> {
            if (user.getCurrentCart().getProducts().isEmpty()) {
                AlertUtils.showError("O carrinho está vazio. Adicione produtos antes de confirmar.");
                return;
            }
            currentStage.getScene().setRoot(new OrderConfirmation(user).showConfirmation(currentStage));
        });

        btnVoltar.getStyleClass().add("btn-primary");
        btnRemover.getStyleClass().add("btn-primary");
        btnConfirmar.getStyleClass().add("btn-primary");

        HBox botoes = new HBox(10, btnVoltar, new Separator(), btnRemover, btnConfirmar);
        botoes.setAlignment(Pos.CENTER);
        botoes.setPadding(new Insets(10));

        totalLabel.setFont(new Font(16));

        VBox root = new VBox(10, tabela, botoes, totalLabel);
        root.setPadding(new Insets(15));

        return root;
    }


    private void voltar(Stage stage) {
        stage.getScene().setRoot(new CatalogView(stage, user).getView(stage));
    }

    private void removerSelecionado() {
        CartProduct selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            user.getCurrentCart().removeProduct(selecionado);
            atualizarTotal();
            tabela.setItems(FXCollections.observableArrayList(user.getCurrentCart().getProducts()));
        } else {
            mostrarErro("Selecione um item para remover.");
        }
    }

    private void alterarQuantidade(int delta) {
        CartProduct selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            int newQuantity = selecionado.getQuantity() + delta;
            if (newQuantity > 0) {
                selecionado.setQuantity(newQuantity);
                tabela.refresh(); // Refresh to show updated values
                atualizarTotal();
            } else {
                user.getCurrentCart().removeProduct(selecionado);
                atualizarTotal();
            }
        } else {
            mostrarErro("Selecione um item para alterar a quantidade.");
        }
    }

    private void atualizarTotal() {
        double total = user.getCurrentCart().getProducts().stream().mapToDouble(CartProduct::getSubtotal).sum();
        totalLabel.setText(String.format("Total: R$ %.2f", total));
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public void showCart(Stage stage) {
        stage.getScene().setRoot(getView(stage));
    }
}
