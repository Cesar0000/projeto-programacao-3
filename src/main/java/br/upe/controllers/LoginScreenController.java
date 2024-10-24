package br.upe.controllers;

import br.upe.userInterface.AppContext;
import br.upe.services.UserService;
import br.upe.services.AuthenticationService;
import br.upe.userInterface.AppContext;
import br.upe.exceptions.authentication.EmailNotRegisteredException;
import br.upe.exceptions.authentication.IncorrectPasswordException;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginScreenController {
    private UserService userService = new UserService();
    private AuthenticationService authenticationService = new AuthenticationService();

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
    private void initialize() {
        emailField.setOnAction(e -> {
            validateEmailField();
        });
        passwordField.setOnAction(e -> {
            validatePasswordField();
        });

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
        String email = emailField.getText();
        String password = passwordField.getText();

        if (!(userService.validateEmail(email) && userService.validatePassword(password))) {
            showError("Erro ao entrar", "Os dados estão inválidos.");
            return;
        }

        MessageDigest messageDigest;

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException error) {
            showError("Erro ao entrar", "Ocorreu um erro inesperado durante o hashing the senha.");
            error.printStackTrace();
            return;
        }

        byte[] passwordHash = messageDigest.digest(password.getBytes());

        loginButton.setDisable(true);
        loginButton.setText("Entrando...");
        registerButton.setDisable(true);

        Task<Void> loginTask = authenticationService.getLoginTask(email, passwordHash);

        loginTask.setOnSucceeded(e -> {
            try {
                Parent homeScreen = FXMLLoader.load(getClass().getResource("/fxml/screens/HomeScreen.fxml"));
                Stage mainStage = AppContext.mainStage;
                mainStage.getScene().setRoot(homeScreen);
            }
            catch (IOException error) {
                authenticationService.logout();
                showError(
                    "Erro ao carregar próxima tela",
                    "Um erro inesperado ocorreu durante o carregamento da tela inicial."
                );

                loginButton.setDisable(false);
                loginButton.setText("Entrar");
                registerButton.setDisable(false);
            }
        });

        loginTask.setOnFailed(e -> {
            Throwable error = loginTask.getException();
            if (error instanceof EmailNotRegisteredException) {
                showError("Erro ao entrar", "O e-mail não está registrado.");
            }
            else if (error instanceof IncorrectPasswordException) {
                showError("Erro ao entrar", "A senha está incorreta.");
            }
            else if (error instanceof SQLException) {
                showError("Erro ao entrar", "Ocorreu um erro ao acessar banco de dados.");
                error.printStackTrace();
            }
            else {
                showError("Erro ao entrar", "Um erro inesperado ocorreu.");
            }

            loginButton.setDisable(false);
            loginButton.setText("Entrar");
            registerButton.setDisable(false);
        });

        ExecutorService threadPool = AppContext.threadPool;
        threadPool.submit(loginTask);
    }

    @FXML
    private void handleRegisterButtonClick() {
        try {
            Parent registerScreen = FXMLLoader.load(getClass().getResource("/fxml/screens/RegisterScreen.fxml"));
            Stage mainStage = AppContext.mainStage;
            mainStage.getScene().setRoot(registerScreen);
        }
        catch (IOException error) {
            error.printStackTrace();

            Alert alert = new Alert(AlertType.ERROR);
            showError(
                "Erro ao carregar próxima tela",
                "Um erro inesperado ocorreu durante o carregamento da tela de registro."
            );
        }
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

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
