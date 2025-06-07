package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.CartProduct;
import br.com.styleoverflow.styleoverflow.classes.Product;
import br.com.styleoverflow.styleoverflow.enums.Gender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class CartView {

    private final TableView<Product> tabela;
    private final ObservableList<Product> produtos;
    private final Label totalLabel;

    public CartView(List<Product> produtos) {
        tabela = new TableView<>();
        this.produtos = FXCollections.observableArrayList(produtos);
        totalLabel = new Label(String.format("Total: R$ %.2f", produtos.stream().mapToDouble(Product::getPrice).sum()));
    }


    public Parent getView(Stage currentStage) {
        // Colunas
        TableColumn<Product, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Integer> colQtd = new TableColumn<>("Quantidade");
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Product, Double> colPreco = new TableColumn<>("Preço Unit.");
        colPreco.setCellValueFactory(new PropertyValueFactory<>("price"));

        tabela.getColumns().addAll(colNome, colQtd, colPreco);
        tabela.setItems(produtos);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Botões
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e ->voltar(currentStage));

        Button btnRemover = new Button("Remover Selecionado");
        btnRemover.setOnAction(e -> removerSelecionado());

        Button btnConfirmar = new Button("Confirmar compra");
        btnConfirmar.setOnAction(e -> currentStage.getScene().setRoot(OrderConfirmation.showConfirmation(currentStage, new ArrayList<>())));

        btnVoltar.getStyleClass().add("btn-primary");
        btnRemover.getStyleClass().add("btn-primary");
        btnConfirmar.getStyleClass().add("btn-primary");

        HBox botoes = new HBox(10, btnVoltar, new Separator(), btnRemover, btnConfirmar);
        botoes.setAlignment(Pos.CENTER);
        botoes.setPadding(new Insets(10));

        // Total
        totalLabel.setFont(new Font(16));

        VBox root = new VBox(10, tabela, botoes, totalLabel);
        root.setPadding(new Insets(15));

        return root;
    }

    private void voltar(Stage stage) {
        stage.getScene().setRoot(new CatalogView(stage).getView(stage));
    }

    private void removerSelecionado() {
        Product selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            produtos.remove(selecionado);
            atualizarTotal();
        } else {
            mostrarErro("Selecione um item para remover.");
        }
    }

    private void atualizarTotal() {
        double total = produtos.stream().mapToDouble(Product::getPrice).sum();
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
