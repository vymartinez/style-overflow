package br.com.styleoverflow.styleoverflow.screens;

import br.com.styleoverflow.styleoverflow.classes.CartProduct;
import br.com.styleoverflow.styleoverflow.DomainException;
import br.com.styleoverflow.styleoverflow.classes.User;
import br.com.styleoverflow.styleoverflow.services.UserService;
import br.com.styleoverflow.styleoverflow.utils.AlertUtils;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;


public class UserProfile {
    private static final UserService userService = new UserService();

    public static Parent showProfile(Stage stage, List<CartProduct> cartProducts, User user) {
        User currentUser = userService.getLoggedInUser();

        if (currentUser == null) {
            AlertUtils.showError("Nenhum usuário logado. Por favor, faça login.");
            stage.getScene().setRoot(LoginAndRegister.showLogin(stage));
            return new VBox();
        }

        VBox root = new VBox(30);
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("root");

        Label title = new Label("Perfil do Usuário");
        title.getStyleClass().add("text-primary");

        Circle avatarCircle = new Circle(50, Color.web("#6c63ff"));
        Label initialsLabel = new Label(getInitials(currentUser.getName()));
        initialsLabel.setTextFill(Color.WHITE);
        initialsLabel.setFont(Font.font("Arial", 28));
        StackPane avatarPane = new StackPane(avatarCircle, initialsLabel);

        Label nameTitle = new Label("Nome:");
        Label nameLabel = new Label(currentUser.getName());

        Label emailTitle = new Label("Email:");
        Label emailLabel = new Label(currentUser.getEmail());

        Label passwordTitle = new Label("Senha:");
        Label passwordMaskedLabel = new Label("********");

        Label cellphoneTitle = new Label("Celular:");
        Label cellphoneLabel = new Label(currentUser.getCellphone());

        Label cepTitle = new Label("CEP:");
        Label cepLabel = new Label(currentUser.getCep());

        Label addressTitle = new Label("Endereço:");
        Label addressLabel = new Label(currentUser.getAddress());

        TextField nameField = new TextField(currentUser.getName());
        nameField.setPromptText("Nome");
        nameField.getStyleClass().add("max-fit");
        nameField.setEditable(false);
        nameField.setVisible(false);

        TextField emailField = new TextField(currentUser.getEmail());
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("max-fit");
        emailField.setVisible(false);

        TextField passwordField = new PasswordField();
        passwordField.setPromptText("Nova Senha (deixe em branco para não alterar)");
        passwordField.getStyleClass().add("max-fit");
        passwordField.setVisible(false);

        TextField cellphoneField = new TextField(currentUser.getCellphone());
        cellphoneField.setPromptText("Celular");
        cellphoneField.getStyleClass().add("max-fit");
        cellphoneField.setVisible(false);

        TextField cepField = new TextField(currentUser.getCep());
        cepField.setPromptText("CEP");
        cepField.getStyleClass().add("max-fit");
        cepField.setVisible(false);

        TextField addressField = new TextField(currentUser.getAddress());
        addressField.setPromptText("Endereço");
        addressField.getStyleClass().add("max-fit");
        addressField.setVisible(false);

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
        VBox passwordBox = new VBox(2, passwordTitle, passwordMaskedLabel, passwordField);
        VBox cellphoneBox = new VBox(2, cellphoneTitle, cellphoneLabel, cellphoneField);
        VBox cepBox = new VBox(2, cepTitle, cepLabel, cepField);
        VBox addressBox = new VBox(2, addressTitle, addressLabel, addressField);

        nameBox.setAlignment(Pos.CENTER);
        emailBox.setAlignment(Pos.CENTER);
        passwordBox.setAlignment(Pos.CENTER);
        cellphoneBox.setAlignment(Pos.CENTER);
        cepBox.setAlignment(Pos.CENTER);
        addressBox.setAlignment(Pos.CENTER);

        VBox fieldsBox = new VBox(15, nameBox, emailBox, passwordBox, cellphoneBox, cepBox, addressBox);
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
            nameLabel.setVisible(true);
            emailLabel.setVisible(false);
            passwordMaskedLabel.setVisible(false);
            cellphoneLabel.setVisible(false);
            cepLabel.setVisible(false);
            addressLabel.setVisible(false);

            nameField.setVisible(false);
            emailField.setVisible(true);
            passwordField.setVisible(true);
            cellphoneField.setVisible(true);
            cepField.setVisible(true);
            addressField.setVisible(true);

            editButton.setVisible(false);
            saveButton.setVisible(true);
            cancelButton.setVisible(true);
        });

        cancelButton.setOnAction(e -> {

            emailField.setText(currentUser.getEmail());
            passwordField.setText("");
            cellphoneField.setText(currentUser.getCellphone());
            cepField.setText(currentUser.getCep());
            addressField.setText(currentUser.getAddress());

            emailField.setVisible(false);
            passwordField.setVisible(false);
            cellphoneField.setVisible(false);
            cepField.setVisible(false);
            addressField.setVisible(false);

            emailLabel.setVisible(true);
            passwordMaskedLabel.setVisible(true);
            cellphoneLabel.setVisible(true);
            cepLabel.setVisible(true);
            addressLabel.setVisible(true);

            editButton.setVisible(true);
            saveButton.setVisible(false);
            cancelButton.setVisible(false);
            feedbackLabel.setVisible(false);
        });

        saveButton.setOnAction(e -> {
            String newEmail = emailField.getText();
            String newPassword = passwordField.getText();
            String newCellphone = cellphoneField.getText();
            String newCep = cepField.getText();
            String newAddress = addressField.getText();


            if (newEmail.isEmpty()) {
                feedbackLabel.setText("Email é obrigatório.");
                feedbackLabel.getStyleClass().setAll("text-danger");
                feedbackLabel.setVisible(true);
                return;
            }

            try {
                userService.updateUser(newEmail, newPassword,
                        newCellphone, newCep, newAddress,
                        currentUser.getId());

                // Atualiza currentUser na memória para refletir as alterações
                currentUser.setEmail(newEmail);
                currentUser.setCellphone(newCellphone);
                currentUser.setCep(newCep);
                currentUser.setAddress(newAddress);

                emailLabel.setText(newEmail);
                cellphoneLabel.setText(newCellphone);
                cepLabel.setText(newCep);
                addressLabel.setText(newAddress);

                feedbackLabel.setText("Perfil atualizado com sucesso.");
                feedbackLabel.getStyleClass().setAll("text-success");
                feedbackLabel.setVisible(true);

                passwordField.setText("");

                cancelButton.fire();
            } catch (DomainException ex) {
                feedbackLabel.setText("Erro ao atualizar perfil: " + ex.getMessage());
                feedbackLabel.getStyleClass().setAll("text-danger");
                feedbackLabel.setVisible(true);
            } catch (RuntimeException ex) {
                feedbackLabel.setText("Erro inesperado: " + ex.getMessage());
                feedbackLabel.getStyleClass().setAll("text-danger");
                feedbackLabel.setVisible(true);
            }
        });

        deleteButton.setOnAction(e -> {
            AlertUtils.showConfirmation("Tem certeza que deseja excluir sua conta? Esta ação é irreversível.", () -> {
                try {
                    userService.deleteUser(currentUser.getId());
                    LoginAndRegister.loggedInUser = null;
                    stage.getScene().setRoot(LoginAndRegister.showLogin(stage));
                } catch (Exception ex) {
                    AlertUtils.showError("Erro ao excluir usuário: " + ex.getMessage());
                }
            });
        });

        backButton.setOnAction(e -> stage.getScene().setRoot(new CatalogView(stage, cartProducts, currentUser).getView(stage)));

        historyButton.setOnAction(e -> {
            Parent historyView = new OrderHistory(cartProducts).getView(stage, currentUser);
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
