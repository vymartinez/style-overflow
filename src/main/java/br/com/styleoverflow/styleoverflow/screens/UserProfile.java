package br.com.styleoverflow.styleoverflow.screens;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
        Label initialsLabel = new Label("UN");
        initialsLabel.setTextFill(Color.WHITE);
        initialsLabel.setFont(Font.font("Arial", 28));
        StackPane avatarPane = new StackPane(avatarCircle, initialsLabel);

        // Labels e Inputs
        Label nameLabel = new Label("User Name");
        Label emailLabel = new Label("User Email");
        Label bioLabel = new Label("Bio do usuário");

        nameLabel.getStyleClass().add("label");
        emailLabel.getStyleClass().add("label");
        bioLabel.getStyleClass().add("label");

        TextField nameField = new TextField();
        nameField.setPromptText("Nome");
        nameField.getStyleClass().add("max-fit");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("max-fit");

        TextArea bioArea = new TextArea();
        bioArea.setPromptText("Bio");
        bioArea.setPrefRowCount(4);
        bioArea.getStyleClass().add("max-fit");

        nameField.setVisible(false);
        emailField.setVisible(false);
        bioArea.setVisible(false);

        Label feedbackLabel = new Label();
        feedbackLabel.setVisible(false);

        Button editButton = new Button("Editar");
        Button saveButton = new Button("Salvar");
        Button cancelButton = new Button("Cancelar");
        Button deleteButton = new Button("Excluir");

        editButton.getStyleClass().add("btn-primary");
        saveButton.getStyleClass().add("btn-primary");
        cancelButton.getStyleClass().add("btn-primary");
        deleteButton.getStyleClass().add("btn-primary-danger");

        saveButton.setVisible(false);
        cancelButton.setVisible(false);

        VBox nameBox = new VBox(5, nameLabel, nameField);
        VBox emailBox = new VBox(5, emailLabel, emailField);
        VBox bioBox = new VBox(5, bioLabel, bioArea);

        nameBox.setAlignment(Pos.CENTER);
        emailBox.setAlignment(Pos.CENTER);
        bioBox.setAlignment(Pos.CENTER);

        VBox fieldsBox = new VBox(15, nameBox, emailBox, bioBox);
        fieldsBox.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(15, editButton, saveButton, cancelButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox content = new VBox(30, title, avatarPane, fieldsBox, feedbackLabel, buttonBox);
        content.setAlignment(Pos.TOP_CENTER);

        root.getChildren().add(content);

        // Eventos
        editButton.setOnAction(e -> {
            nameLabel.setVisible(false);
            emailLabel.setVisible(false);
            bioLabel.setVisible(false);

            nameField.setVisible(true);
            emailField.setVisible(true);
            bioArea.setVisible(true);

            editButton.setVisible(false);
            saveButton.setVisible(true);
            cancelButton.setVisible(true);
        });

        cancelButton.setOnAction(e -> {
            nameField.setVisible(false);
            emailField.setVisible(false);
            bioArea.setVisible(false);

            nameLabel.setVisible(true);
            emailLabel.setVisible(true);
            bioLabel.setVisible(true);

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
            bioLabel.setText(bioArea.getText());

            initialsLabel.setText(getInitials(nameField.getText()));

            feedbackLabel.setText("Perfil atualizado com sucesso.");
            feedbackLabel.getStyleClass().setAll("text-success");
            feedbackLabel.setVisible(true);

            cancelButton.fire();
        });

        deleteButton.setOnAction(e -> {
            nameLabel.setText("User Name");
            emailLabel.setText("User Email");
            bioLabel.setText("Bio do usuário");
            initialsLabel.setText("UN");

            feedbackLabel.setText("Perfil excluído.");
            feedbackLabel.getStyleClass().setAll("text-danger");
            feedbackLabel.setVisible(true);
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
