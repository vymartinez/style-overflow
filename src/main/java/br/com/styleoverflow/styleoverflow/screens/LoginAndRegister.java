package br.com.styleoverflow.styleoverflow.screens;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginAndRegister {

    public static Parent showLogin(Stage stage) {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(15);
        root.getStyleClass().add("root");

        Label title = new Label("Style Overflow");
        title.setFont(Font.font("Fira Code", FontWeight.BOLD, 24));
        title.getStyleClass().add("text-primary");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("max-fit");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Senha");
        passwordField.getStyleClass().add("max-fit");

        Button loginBtn = new Button("Entrar");
        loginBtn.getStyleClass().add("btn-primary");

        Label feedback = new Label();

        loginBtn.setOnAction(e -> {
            if (emailField.getText().equals("admin") &&
                    passwordField.getText().equals("admin")) {
                stage.getScene().setRoot(new AdminDashboard().getView(stage));
            } else {
                feedback.setText("Credenciais inválidas.");
                feedback.getStyleClass().setAll("text-danger");
            }
        });

        Hyperlink toRegister = new Hyperlink("Criar conta");
        toRegister.setOnAction(e -> stage.getScene().setRoot(showRegister(stage)));

        root.getChildren().addAll(title, emailField, passwordField, loginBtn, feedback, toRegister);
        return root;
    }

    public static Parent showRegister(Stage stage) {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(15);
        root.getStyleClass().add("root");

        Label title = new Label("Cadastro");
        title.getStyleClass().add("text-primary");

        TextField nameField = new TextField();
        nameField.setPromptText("Nome completo");
        nameField.getStyleClass().add("max-fit");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("max-fit");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Senha");
        passwordField.getStyleClass().add("max-fit");

        TextField cellphoneField = new TextField();
        cellphoneField.setPromptText("Celular");
        cellphoneField.getStyleClass().add("max-fit");

        ComboBox<String> genderBox = new ComboBox<>();
        genderBox.getItems().addAll("Masculino", "Feminino");
        genderBox.setPromptText("Gênero");
        genderBox.getStyleClass().add("max-fit");

        Button registerBtn = new Button("Registrar");
        registerBtn.getStyleClass().add("btn-primary");

        Label feedback = new Label();

        registerBtn.setOnAction(e -> {
            if (emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                feedback.setText("Preencha todos os campos obrigatórios.");
                feedback.getStyleClass().setAll("text-danger");
            } else {
                feedback.setText("Conta criada com sucesso!");
                feedback.getStyleClass().setAll("text-success");
            }
        });

        Hyperlink toLogin = new Hyperlink("Já tem conta? Entrar");
        toLogin.setOnAction(e -> stage.getScene().setRoot(showLogin(stage)));

        root.getChildren().addAll(title, nameField, emailField, passwordField, cellphoneField, genderBox, registerBtn, feedback, toLogin);
        return root;
    }
}
