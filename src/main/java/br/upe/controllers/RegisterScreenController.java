package br.upe.controllers;

import br.upe.userInterface.AppContext;
import br.upe.services.UserService;
import br.upe.models.User;
import br.upe.exceptions.registration.EmailAlreadyRegisteredException;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.nio.charset.StandardCharsets;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RegisterScreenController {
    private UserService userService = new UserService();

    @FXML
    private TextField nameField;

    @FXML
    private Label nameValidationLabel;

    @FXML
    private TextField emailField;

    @FXML
    private Label emailValidationLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordValidationLabel;

    @FXML
    private Button registerButton;

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        nameField.setOnAction(e -> {
            validateNameField();
        });
        emailField.setOnAction(e -> {
            validateEmailField();
        });
        passwordField.setOnAction(e -> {
            validatePasswordField();
        });

        nameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateNameField();
            }
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
    private void handleRegisterButtonClick() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (
            name.isEmpty()
            || !userService.validateEmail(email)
            || !userService.validatePassword(password)
        ) {
            showError("Erro ao criar conta", "Os dados não são válidos.");
            return;
        }

        MessageDigest messageDigest;

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException error) {
            showError("Erro ao criar conta", "Ocorreu um erro inesperado durante o hashing the senha.");
            error.printStackTrace();
            return;
        }

        byte[] passwordHash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

        registerButton.setDisable(true);
        registerButton.setText("Criando conta...");
        loginButton.setDisable(true);

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(passwordHash);

        Task<Void> registerUserTask = userService.getRegisterUserTask(newUser);

        registerUserTask.setOnSucceeded(e -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Conta criada");
            alert.setHeaderText(null);
            alert.setContentText("A sua conta foi criada com sucesso.");
    
            alert.showAndWait();

            try {
                Parent loginScreen = FXMLLoader.load(getClass().getResource("/fxml/screens/LoginScreen.fxml"));
                Stage mainStage = AppContext.mainStage;
                mainStage.getScene().setRoot(loginScreen);
            }
            catch (IOException error) {
                showError(
                    "Erro ao carregar a próxima tela",
                    "Um erro inesperado ocorreu durante o carregamento da tela de login."
                );

                registerButton.setDisable(false);
                registerButton.setText("Criar conta");
                loginButton.setDisable(false);                
            }
        });

        registerUserTask.setOnFailed(e -> {
            Throwable error = registerUserTask.getException();
            if (error instanceof EmailAlreadyRegisteredException) {
                showError("Erro ao criar conta", "O e-mail já está registrado.");
            }
            else if (error instanceof SQLException) {
                showError("Erro ao criar conta", "Ocorreu um erro ao acessar o banco de dados.");
                error.printStackTrace();
            }
            else {
                showError("Erro ao criar conta", "Um erro inesperado ocorreu.");
                error.printStackTrace();
            }

            registerButton.setDisable(false);
            registerButton.setText("Criar conta");
            loginButton.setDisable(false);      
        });

        ExecutorService threadPool = AppContext.threadPool;
        threadPool.submit(registerUserTask);
    }

    @FXML
    private void handleLoginButtonClick() {
        try {
            Parent loginScreen = FXMLLoader.load(getClass().getResource("/fxml/screens/LoginScreen.fxml"));
            Stage mainStage = AppContext.mainStage;
            mainStage.getScene().setRoot(loginScreen);
        }
        catch (IOException error) {
            error.printStackTrace();

            Alert alert = new Alert(AlertType.ERROR);
            showError(
                "Erro ao carregar próxima tela",
                "Um erro inesperado ocorreu durante o carregamento da tela de login."
            );
        }
    }

    private void validateNameField() {
        String name = nameField.getText();

        if (name.isEmpty()) {
            nameValidationLabel.setText("O campo do nome não pode estar vazio.");
            nameValidationLabel.setStyle("-fx-text-fill: red;");
        }
        else {
            nameValidationLabel.setText("O nome é válido.");
            nameValidationLabel.setStyle("-fx-text-fill: green;");
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
