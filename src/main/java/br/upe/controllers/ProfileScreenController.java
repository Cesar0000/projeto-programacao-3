package br.upe.controllers;

import br.upe.services.AuthenticationService;
import br.upe.userinterface.AppContext;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ProfileScreenController {
    private AuthenticationService authenticationService = new AuthenticationService();

    @FXML
    private Button returnButton;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userEmailLabel;

    @FXML
    private Button changePasswordButton;

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private Button confirmPasswordChangeButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button deleteProfileButton;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleReturnButtonClick() {
        Parent homeScreen;

        try {
            homeScreen = FXMLLoader.load(getClass().getResource("/fxml/screens/HomeScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();

            showError(
                "Erro ao carregar a próxima tela",
                "Um erro inesperado ocorreu durante o carregamento da tela inicial."
            );

            return;
        }

        Stage mainStage = AppContext.mainStage;
        mainStage.getScene().setRoot(homeScreen);
    }

    @FXML
    private void handleChangePasswordButtonClick() {
    }

    @FXML
    private void handleConfirmPasswordChangeButtonClick() {
    }

    @FXML
    private void handleExitButtonClick() {
        Parent loginScreen;

        try {
            loginScreen = FXMLLoader.load(getClass().getResource("/fxml/screens/LoginScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();

            showError(
                "Erro ao carregar a próxima tela",
                "Um erro inesperado ocorreu durante o carregamento da tela de login."
            );

            return;
        }

        authenticationService.logout();
        Stage mainStage = AppContext.mainStage;
        mainStage.getScene().setRoot(loginScreen);
    }

    @FXML
    private void handleDeleteProfileButtonClick() {
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
