package br.upe.controllers;

import br.upe.userinterface.AppContext;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeScreenController {
    @FXML
    private Button exitButton;

    @FXML
    private Button configurationButton;

    @FXML
    private Button accessEventsButton;

    @FXML
    private Button accessSubscriptionsButton;

    @FXML
    private Button accessSubmissionsButton;

    @FXML
    private Button accessProfileButton;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleExitButtonClick() {
        try {
            Parent loginScreen = FXMLLoader.load(getClass().getResource("/fxml/screens/LoginScreen.fxml"));
            Stage mainStage = AppContext.mainStage;
            mainStage.getScene().setRoot(loginScreen);
        } catch (IOException error) {
            error.printStackTrace();

            showError(
                "Erro ao carregar a próxima tela",
                "Um erro inesperado ocorreu durante o carregamento da tela de login."
            );
        }
    }

    @FXML
    private void handleConfigurationButtonClick() {

    }

    @FXML
    private void handleAccessEventsButtonClick() {
        try {
            Parent eventsScreen = FXMLLoader.load(getClass().getResource("/fxml/screens/EventsScreen.fxml"));
            Stage mainStage = AppContext.mainStage;
            mainStage.getScene().setRoot(eventsScreen);
        } catch (IOException e) {
            e.printStackTrace();

            showError(
                "Erro ao carregar a próxima tela",
                "Um erro inesperado ocorreu durante o carregamento da tela de listagem dos eventos."
            );
        }
    }

    @FXML
    private void handleAccessSubscriptionsButtonClick() {

    }

    @FXML
    private void handleAccessSubmissionsButtonClick() {

    }

    @FXML
    private void handleAccessProfileButtonClick() {

    }

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
