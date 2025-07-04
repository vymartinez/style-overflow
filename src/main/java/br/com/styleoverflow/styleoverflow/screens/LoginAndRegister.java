package br.com.styleoverflow.styleoverflow.screens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.styleoverflow.styleoverflow.DomainException;
import br.com.styleoverflow.styleoverflow.classes.Admin;
import br.com.styleoverflow.styleoverflow.classes.User;
import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Role;
import br.com.styleoverflow.styleoverflow.services.UserService;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginAndRegister {

    private final UserService userService = new UserService();

    public Parent showLogin(Stage stage) {
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
            String email = emailField.getText();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                feedback.setText("Por favor, preencha todos os campos.");
                feedback.getStyleClass().setAll("text-danger");
                return;
            }

            try {
                User user = userService.login(email, password); // Chama o serviço de login

                feedback.setText("Login realizado com sucesso! Bem-vindo, " + user.getName() + "!");
                feedback.getStyleClass().setAll("text-success");

                if (user.getRole() == Role.ADMIN) {
                    Admin admin = (Admin) user;
                    admin.login(stage);
                }
                else user.login(stage);

            } catch (DomainException ex) {
                feedback.setText("Erro: " + ex.getMessage());
                feedback.getStyleClass().setAll("text-danger");
                ex.printStackTrace();
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
                feedback.setText(ex.getMessage());
                feedback.getStyleClass().setAll("text-danger");
                ex.printStackTrace();
            }
        });

        Hyperlink toRegister = new Hyperlink("Criar conta");
        toRegister.setOnAction(e -> stage.getScene().setRoot(showRegister(stage)));

        root.getChildren().addAll(title, emailField, passwordField, loginBtn, feedback, toRegister);
        return root;
    }

    public Parent showRegister(Stage stage) {
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
        genderBox.getItems().addAll(Arrays.stream(Gender.values()).map(Gender::toPortugueseString).toList());
        genderBox.setPromptText("Gênero");
        genderBox.getStyleClass().add("max-fit");

        TextField addressField = new TextField();
        addressField.setPromptText("Endereço");
        addressField.getStyleClass().add("max-fit");

        TextField cpfField = new TextField();
        cpfField.setPromptText("CPF");
        cpfField.getStyleClass().add("max-fit");

        TextField cepField = new TextField();
        cepField.setPromptText("CEP");
        cepField.getStyleClass().add("max-fit");

        Button registerBtn = new Button("Registrar");
        registerBtn.getStyleClass().add("btn-primary");

        Label feedback = new Label();

        registerBtn.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String cellphone = cellphoneField.getText();
            String cpf = cpfField.getText();
            String cep = cepField.getText();
            String address = addressField.getText();
            String genderString = genderBox.getValue();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() ||
                cellphone.isEmpty() || cpf.isEmpty() || cep.isEmpty() ||
                address.isEmpty() || genderString == null) {
                feedback.setText("Por favor, preencha todos os campos obrigatórios.");
                feedback.getStyleClass().setAll("text-danger");
                return;
            }

            try {
                Gender gender = genderString.equals("Masculino") ? Gender.MALE : Gender.FEMALE; // Converte String para Enum Gender

                // Chama o serviço para criar o usuário
                userService.createUser(name, email, password, cellphone, cpf, cep, address, gender);

                feedback.setText("Conta criada com sucesso! Você já pode fazer login.");
                feedback.getStyleClass().add("text-success");

                // Limpar campos após o sucesso
                nameField.clear();
                emailField.clear();
                passwordField.clear();
                cellphoneField.clear();
                cpfField.clear();
                cepField.clear();
                addressField.clear();
                genderBox.getSelectionModel().clearSelection();

            } catch (IllegalArgumentException ex) {
                feedback.setText("Erro no gênero selecionado.");
                feedback.getStyleClass().setAll("text-danger");
                ex.printStackTrace();
            } catch (DomainException ex) {
                feedback.setText("Erro ao criar conta: " + ex.getMessage());
                feedback.getStyleClass().setAll("text-danger");
                ex.printStackTrace();
            } catch (RuntimeException ex) {
                feedback.setText("Erro interno do sistema ao criar conta.");
                feedback.getStyleClass().setAll("text-danger");
                ex.printStackTrace();
            }
        });

        Hyperlink toLogin = new Hyperlink("Já tem conta? Entrar");
        toLogin.setOnAction(e -> stage.getScene().setRoot(showLogin(stage)));

        root.getChildren().addAll(title, nameField, emailField, passwordField, cellphoneField, genderBox, cpfField, cepField, addressField, registerBtn, feedback, toLogin);
        return root;
    }
}
