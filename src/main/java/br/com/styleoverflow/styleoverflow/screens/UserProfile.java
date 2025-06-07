package br.com.styleoverflow.styleoverflow.screens;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UserProfile {

    public static Parent showProfile(Stage stage) {
        VBox root = new VBox(30);
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("root");

        Label title = new Label("Perfil do Usuário");
        title.getStyleClass().add("text-primary");

        // Avatar
        Circle avatarCircle = new Circle(50, Color.web("#6c63ff"));
        Label initialsLabel = new Label("U");
        initialsLabel.setTextFill(Color.WHITE);
        initialsLabel.setFont(Font.font("Arial", 28));
        StackPane avatarPane = new StackPane(avatarCircle, initialsLabel);

        // Labels e Inputs
        Label nameTitle = new Label("Nome:");
        Label nameLabel = new Label("");

        Label emailTitle = new Label("Email:");
        Label emailLabel = new Label("");

        TextField nameField = new TextField();
        nameField.setPromptText("Nome");
        nameField.getStyleClass().add("max-fit");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("max-fit");

        nameField.setVisible(false);
        emailField.setVisible(false);

        Label feedbackLabel = new Label();
        feedbackLabel.setVisible(false);

        Button editButton = new Button("Editar");
        Button saveButton = new Button("Salvar");
        Button cancelButton = new Button("Cancelar");
        Button deleteButton = new Button("Excluir");
        Button backButton = new Button("Voltar");

        Button historyButton = new Button("Histórico de Pedidos");

        editButton.getStyleClass().add("btn-primary");
        saveButton.getStyleClass().add("btn-primary");
        cancelButton.getStyleClass().add("btn-primary");
        deleteButton.getStyleClass().add("btn-primary-danger");
        backButton.getStyleClass().add("btn-primary");
        historyButton.getStyleClass().add("btn-primary");

        saveButton.setVisible(false);
        cancelButton.setVisible(false);

        VBox nameBox = new VBox(2, nameTitle, nameLabel, nameField);
        VBox emailBox = new VBox(2, emailTitle, emailLabel, emailField);

        nameBox.setAlignment(Pos.CENTER);
        emailBox.setAlignment(Pos.CENTER);

        VBox fieldsBox = new VBox(15, nameBox, emailBox);
        fieldsBox.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(15, editButton, saveButton, cancelButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);

        HBox navButtons = new HBox(15, backButton, historyButton);
        navButtons.setAlignment(Pos.CENTER);

        VBox content = new VBox(30, title, avatarPane, fieldsBox, feedbackLabel, buttonBox, navButtons);
        content.setAlignment(Pos.TOP_CENTER);

        root.getChildren().add(content);

        // Eventos
        editButton.setOnAction(e -> {
            nameLabel.setVisible(false);
            emailLabel.setVisible(false);
            nameField.setVisible(true);
            emailField.setVisible(true);
            editButton.setVisible(false);
            saveButton.setVisible(true);
            cancelButton.setVisible(true);
        });

        cancelButton.setOnAction(e -> {
            nameField.setVisible(false);
            emailField.setVisible(false);
            nameLabel.setVisible(true);
            emailLabel.setVisible(true);
            editButton.setVisible(true);
            saveButton.setVisible(false);
            cancelButton.setVisible(false);
            feedbackLabel.setVisible(false);
        });

        saveButton.setOnAction(e -> {
            if (nameField.getText().isEmpty() || emailField.getText().isEmpty()) {
                feedbackLabel.setText("Nome e email são obrigatórios.");
                feedbackLabel.getStyleClass().setAll("text-danger");
                feedbackLabel.setVisible(true);
                return;
            }

            nameLabel.setText(nameField.getText());
            emailLabel.setText(emailField.getText());
            initialsLabel.setText(getInitials(nameField.getText()));
            feedbackLabel.setText("Perfil atualizado com sucesso.");
            feedbackLabel.getStyleClass().setAll("text-success");
            feedbackLabel.setVisible(true);

            cancelButton.fire();
        });

        deleteButton.setOnAction(e -> {
            feedbackLabel.setText("Perfil excluído.");
            feedbackLabel.getStyleClass().setAll("text-danger");
            feedbackLabel.setVisible(true);
        });

        backButton.setOnAction(e -> new CatalogView(stage).getView(stage));

        historyButton.setOnAction(e -> {
            Parent historyView = new OrderHistory().getView(stage);
            stage.getScene().setRoot(historyView);
        });


        return root;
    }

    private static String getInitials(String name) {
        String[] parts = name.trim().split(" ");
        StringBuilder initials = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) initials.append(part.charAt(0));
            if (initials.length() == 2) break;
        }
        return initials.toString().toUpperCase();
    }
}
