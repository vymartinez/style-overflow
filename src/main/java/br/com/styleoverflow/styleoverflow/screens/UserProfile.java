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

public class UserProfile extends Parent {

    private VBox profileBox;
    private Label nameLabel, emailLabel, bioLabel, feedbackLabel;
    private TextField nameField, emailField;
    private TextArea bioArea;
    private Button editButton, saveButton, cancelButton, deleteButton;
    private HBox buttonBox;
    private StackPane avatarPane;

    public Parent getUserProfile(Stage stage) {
        profileBox = new VBox(20);
        profileBox.setAlignment(Pos.CENTER);
        profileBox.getStyleClass().add("root");

        // Avatar
        avatarPane = createAvatar("UN");

        Label title = new Label("Perfil do Usuário");
        title.setFont(Font.font("Fira Code", FontWeight.BOLD, 24));
        title.getStyleClass().add("text-primary");

        nameLabel = new Label("User Name");
        emailLabel = new Label("User Email");
        bioLabel = new Label("Bio do usuário");
        bioLabel.setWrapText(true);

        nameField = new TextField();
        nameField.setPromptText("Nome");
        nameField.getStyleClass().add("max-fit");

        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("max-fit");

        bioArea = new TextArea();
        bioArea.setPromptText("Bio");
        bioArea.setPrefRowCount(3);
        bioArea.getStyleClass().add("max-fit");

        nameField.setVisible(false);
        emailField.setVisible(false);
        bioArea.setVisible(false);

        feedbackLabel = new Label();
        feedbackLabel.setVisible(false);

        editButton = new Button("Editar");
        editButton.getStyleClass().add("btn-primary");

        saveButton = new Button("Salvar");
        saveButton.getStyleClass().add("btn-primary");
        saveButton.setVisible(false);

        cancelButton = new Button("Cancelar");
        cancelButton.getStyleClass().add("btn-secondary");
        cancelButton.setVisible(false);

        deleteButton = new Button("Excluir");
        deleteButton.getStyleClass().add("btn-danger");

        buttonBox = new HBox(10, editButton, saveButton, cancelButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox fieldsBox = new VBox(10);
        fieldsBox.setAlignment(Pos.CENTER);
        fieldsBox.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, bioLabel, bioArea);

        profileBox.getChildren().addAll(title, avatarPane, fieldsBox, feedbackLabel, buttonBox);

        // Eventos
        editButton.setOnAction(e -> enterEditMode());
        saveButton.setOnAction(e -> saveProfile());
        cancelButton.setOnAction(e -> exitEditMode());
        deleteButton.setOnAction(e -> deleteProfile());

        return profileBox;
    }

    private StackPane createAvatar(String initials) {
        Circle circle = new Circle(40, Color.web("#6c63ff"));
        Label initialsLabel = new Label(initials);
        initialsLabel.setTextFill(Color.WHITE);
        initialsLabel.setFont(Font.font("Arial", 24));
        StackPane avatar = new StackPane(circle, initialsLabel);
        return avatar;
    }

    private void enterEditMode() {
        nameLabel.setVisible(false);
        emailLabel.setVisible(false);
        bioLabel.setVisible(false);

        nameField.setVisible(true);
        emailField.setVisible(true);
        bioArea.setVisible(true);

        editButton.setVisible(false);
        saveButton.setVisible(true);
        cancelButton.setVisible(true);
    }

    private void exitEditMode() {
        nameLabel.setVisible(true);
        emailLabel.setVisible(true);
        bioLabel.setVisible(true);

        nameField.setVisible(false);
        emailField.setVisible(false);
        bioArea.setVisible(false);

        editButton.setVisible(true);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
    }

    public void saveProfile() {
        if (nameField.getText().isEmpty() || emailField.getText().isEmpty()) {
            feedbackLabel.setText("Nome e email são obrigatórios.");
            feedbackLabel.getStyleClass().setAll("text-danger");
            feedbackLabel.setVisible(true);
            return;
        }

        setName(nameField.getText());
        setEmail(emailField.getText());
        setBio(bioArea.getText());
        feedbackLabel.setText("Perfil atualizado com sucesso.");
        feedbackLabel.getStyleClass().setAll("text-success");
        feedbackLabel.setVisible(true);

        exitEditMode();
    }

    public void deleteProfile() {
        setName("User Name");
        setEmail("User Email");
        setBio("Bio do usuário");
        feedbackLabel.setText("Perfil excluído.");
        feedbackLabel.getStyleClass().setAll("text-danger");
        feedbackLabel.setVisible(true);
    }

    public void setName(String name) {
        nameLabel.setText(name);
        nameField.setText(name);
        updateAvatarInitials(name);
    }

    public void setEmail(String email) {
        emailLabel.setText(email);
        emailField.setText(email);
    }

    public void setBio(String bio) {
        bioLabel.setText(bio);
        bioArea.setText(bio);
    }

    private void updateAvatarInitials(String name) {
        String[] parts = name.trim().split(" ");
        String initials = "";
        for (String part : parts) {
            if (!part.isEmpty()) initials += part.charAt(0);
            if (initials.length() == 2) break;
        }
        Label label = (Label) avatarPane.getChildren().get(1);
        label.setText(initials.toUpperCase());
    }
}
