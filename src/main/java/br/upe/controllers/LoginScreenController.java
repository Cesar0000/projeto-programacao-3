package br.upe.controllers;

import br.upe.userInterface.AppContext;

import java.io.IOException;

import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LoginScreenController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private void handleLoginButtonClick() {
    }

    @FXML
    private void handleRegisterButtonClick() throws IOException {
        Parent registerScreen = FXMLLoader.load(getClass().getResource("/fxml/screens/RegisterScreen.fxml"));
        Stage mainStage = AppContext.mainStage;
        mainStage.getScene().setRoot(registerScreen);
    }
}
