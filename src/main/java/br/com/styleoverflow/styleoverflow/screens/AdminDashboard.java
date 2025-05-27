package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.Order;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class AdminDashboard {

    //private final AdminService adminService = new AdminService();

    public Parent getView() {
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(productTab(), orderTab());

        BorderPane root = new BorderPane(tabPane);
        root.setPadding(new Insets(20));
        return root;
    }

    private Tab productTab() {
        VBox formBox = new VBox(10);
        formBox.setPadding(new Insets(20));

        TextField name = new TextField();
        ComboBox<String> size = new ComboBox<>();
        size.getItems().addAll("PP", "P", "M", "G", "GG");

        ComboBox<String> gender = new ComboBox<>();
        gender.getItems().addAll("MALE", "FEMALE");

        TextField color = new TextField();
        TextField stock = new TextField();
        TextField price = new TextField();

        Button registerBtn = new Button("Registrar Produto");
        registerBtn.getStyleClass().add("btn");
        registerBtn.setOnAction(e -> {
//                adminService.registerProduct(
//                name.getText(), size.getValue(), gender.getValue(), color.getText(),
//                Integer.parseInt(stock.getText()), Double.parseDouble(price.getText()))
        });

        formBox.getChildren().addAll(
                new Label("Nome do Produto:"), name,
                new Label("Tamanho:"), size,
                new Label("Gênero:"), gender,
                new Label("Cor:"), color,
                new Label("Estoque:"), stock,
                new Label("Preço:"), price,
                registerBtn
        );

        Tab tab = new Tab("Produtos", formBox);
        tab.setClosable(false);
        return tab;
    }

    private Tab orderTab() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        TableView<Order> table = new TableView<>();
        table.setPlaceholder(new Label("Sem pedidos."));

//        TableColumn<Order, Integer> idCol = new TableColumn<>("ID");
//        idCol.setCellValueFactory(cell -> cell.getValue().id().asObject());
//
//        TableColumn<Order, String> clientCol = new TableColumn<>("Cliente");
//        clientCol.setCellValueFactory(cell -> cell.getValue().getClient().getName());
//
//        TableColumn<Order, String> dateCol = new TableColumn<>("Data");
//        dateCol.setCellValueFactory(cell -> cell.getValue().getDate());
//
//        TableColumn<Order, String> statusCol = new TableColumn<>("Status");
//        statusCol.setCellValueFactory(cell -> cell.getValue().getStatus());

        //table.getColumns().addAll(idCol, clientCol, dateCol, statusCol);

        Button sortBtn = new Button("Ordenar por Data");
        sortBtn.getStyleClass().add("btn");
        sortBtn.setOnAction(e -> {
//            List<Order> orders = adminService.sortOrdersByDate();
//            table.getItems().setAll(orders);
        });

        vbox.getChildren().addAll(sortBtn, table);

        Tab tab = new Tab("Pedidos", vbox);
        tab.setClosable(false);
        return tab;
    }
}
