package br.upe.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ProfileScreenController {

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
    }

    @FXML
    private void handleChangePasswordButtonClick() {
        // currentPasswordLabel.setVisible(true);
        // currentPasswordField.setVisible(true);
        // newPasswordLabel.setVisible(true);
        // newPasswordField.setVisible(true);
        // confirmButton.setVisible(true);
    }

    @FXML
    private void handleConfirmPasswordChangeButtonClick() {
        // String currentPassword = currentPasswordField.getText();
        // String newPassword = newPasswordField.getText();

        // if (validatePassword(currentPassword, newPassword)) {
        //     //
        //     showAlert("Senha alterada com sucesso!", AlertType.INFORMATION);
        // } else {
        //     showAlert("Falha ao alterar a senha. Verifique os campos!", AlertType.ERROR);
        // }
    }

    @FXML
    private void handleExitButtonClick() {
    }

    @FXML
    private void handleDeleteProfileButtonClick() {
    }
}
