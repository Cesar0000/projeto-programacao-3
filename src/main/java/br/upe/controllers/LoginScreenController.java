package br.upe.controllers;

import br.upe.userInterface.AppContext;
import br.upe.services.UserService;

import java.io.IOException;

import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;

public class LoginScreenController {
    private UserService userService = new UserService();

    @FXML
    private TextField emailField;

    @FXML
    private Label emailValidationLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordValidationLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    public void initialize() {
        emailField.setOnAction(e -> validateEmailField());
        passwordField.setOnAction(e -> validatePasswordField());

        emailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateEmailField();
            }
        });
        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validatePasswordField();
            }
        });
    }

    @FXML
    private void handleLoginButtonClick() {
        // PEGAR O INPUT DO USUÁRIO E VALIDAR
        // DESABILITAR BOTÕES
        // CRIAR TASK E PASSAR PRA THREADPOOL
        // SETAR OS EVENT HANDLERS DA TASK
        // QUANDO APERTAR PARA LOGAR VERFICAR NOVAMENTE OS CAMPOS E SE TIVER ALGUM CAMPO VAZIO OU COM OUTRO PROBLEMA AVISAR
    }

    @FXML
    private void handleRegisterButtonClick() throws IOException {
        Parent registerScreen = FXMLLoader.load(getClass().getResource("/fxml/screens/RegisterScreen.fxml"));
        Stage mainStage = AppContext.mainStage;
        mainStage.getScene().setRoot(registerScreen);
    }

    private void validateEmailField() {
        String email = emailField.getText();

        if (email.isEmpty()) {
            emailValidationLabel.setText("O campo de e-mail não pode estar vazio.");
            emailValidationLabel.setStyle("-fx-text-fill: red;");
        }
        else if (!userService.validateEmail(email)) {
            emailValidationLabel.setText("O e-mail não é válido.");
            emailValidationLabel.setStyle("-fx-text-fill: red;");
        }
        else {
            emailValidationLabel.setText("O email é válido.");
            emailValidationLabel.setStyle("-fx-text-fill: green;");
        }
    }

    private void validatePasswordField() {
        String password = passwordField.getText();

        if (password.isEmpty()) {
            passwordValidationLabel.setText("O campo de senha não pode estar vazio.");
            passwordValidationLabel.setStyle("-fx-text-fill: red;");
        }
        else if (!userService.validatePassword(password)) {
            passwordValidationLabel.setText("A senha não é válida.");
            passwordValidationLabel.setStyle("-fx-text-fill: red;");
        }
        else {
            passwordValidationLabel.setText("A senha é válida.");
            passwordValidationLabel.setStyle("-fx-text-fill: green;");
        }
    }
}
