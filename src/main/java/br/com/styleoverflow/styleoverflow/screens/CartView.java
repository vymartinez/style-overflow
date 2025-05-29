package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class CartView {

    private final TableView<Product> tabela;
    private final ObservableList<Product> produtos;
    private final Label totalLabel;
    private Stage currentStage;

    public CartView() {
        tabela = new TableView<>();
        produtos = FXCollections.observableArrayList();
        totalLabel = new Label("Total: R$ 0.00");
    }


    public Parent getView() {
        // Colunas
        TableColumn<Product, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(p -> p.getValue().nameProperty());

        TableColumn<Product, Integer> colQtd = new TableColumn<>("Quantidade");
        colQtd.setCellValueFactory(p -> p.getValue().quantidadeProperty().asObject());

        TableColumn<Product, Double> colPreco = new TableColumn<>("Preço Unit.");
        colPreco.setCellValueFactory(p -> p.getValue().priceProperty().asObject());

        TableColumn<Product, String> colSubtotal = new TableColumn<>("Subtotal");
        colSubtotal.setCellValueFactory(p ->
                new javafx.beans.property.SimpleStringProperty(
                        String.format("R$ %.2f", p.getValue().getSubtotal())
                ));

        tabela.getColumns().addAll(colNome, colQtd, colPreco, colSubtotal);
        tabela.setItems(produtos);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Botões
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> voltar());

        Button btnRemover = new Button("Remover Selecionado");
        btnRemover.setOnAction(e -> removerSelecionado());

        HBox botoes = new HBox(10, btnRemover, btnVoltar);
        botoes.setPadding(new Insets(10));

        // Total
        totalLabel.setFont(new Font(16));

        VBox root = new VBox(10, tabela, botoes, totalLabel);
        root.setPadding(new Insets(15));

        return root;
    }

    private void voltar() {
        new CatalogView().showCatalog(currentStage); // Retorna para o catálogo
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
        double total = produtos.stream().mapToDouble(Product::getSubtotal).sum();
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
        this.currentStage = stage;
        Scene scene = new Scene(getView(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Carrinho - Style Overflow");
        stage.show();
    }
}
