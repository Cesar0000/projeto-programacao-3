package br.upe.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ProfileScreenController {

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userEmailLabel;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Label currentPasswordLabel;

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private Label newPasswordLabel;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button returnButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button deleteAccountButton;

    // Evento para o botão de retorno
    @FXML
    private void handleReturnButtonClick() {
        // Lógica para retornar à tela anterior
        System.out.println("Botão Voltar pressionado!");
    }

    // Evento para alterar a senha (tornar os campos visíveis)
    @FXML
    private void handleChangePasswordClick() {
        currentPasswordLabel.setVisible(true);
        currentPasswordField.setVisible(true);
        newPasswordLabel.setVisible(true);
        newPasswordField.setVisible(true);
        confirmButton.setVisible(true);
    }

    // Evento para confirmar a alteração da senha
    @FXML
    private void handleConfirmPasswordChangeClick() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();

        if (validatePassword(currentPassword, newPassword)) {
            //
            showAlert("Senha alterada com sucesso!", AlertType.INFORMATION);
        } else {
            showAlert("Falha ao alterar a senha. Verifique os campos!", AlertType.ERROR);
        }
    }


    @FXML
    private void handleLogoutClick() {
        // Lógica para logout
        System.out.println("Logout realizado!");
    }


    @FXML
    private void handleDeleteAccountClick() {
        // Lógica para excluir a conta
        System.out.println("Conta excluída!");
    }


    private boolean validatePassword(String currentPassword, String newPassword) {

        return !currentPassword.isEmpty() && !newPassword.isEmpty() && !currentPassword.equals(newPassword);
    }


    private void showAlert(String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
