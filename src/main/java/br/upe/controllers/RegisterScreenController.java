package br.upe.controllers;

import br.upe.userInterface.AppContext;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RegisterScreenController {
    @FXML
    private TextField nameField;

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

    }

    @FXML
    private void handleRegisterButtonClick() {

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

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
