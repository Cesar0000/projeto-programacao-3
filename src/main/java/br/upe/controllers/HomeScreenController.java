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
        accessProfileButton.setOnAction(event -> handleAccessProfileButtonClick());
        exitButton.setOnAction(event -> handleExitButtonClick());
        configurationButton.setOnAction(event -> handleConfigurationButtonClick());
        accessEventsButton.setOnAction(event -> handleAccessEventsButtonClick());
        accessSubscriptionsButton.setOnAction(event -> handleAccessSubscriptionsButtonClick());
        accessSubmissionsButton.setOnAction(event -> handleAccessSubmissionsButtonClick());
    }

    @FXML
    private void handleExitButtonClick() {
        try {
            Parent loginScreen = FXMLLoader.load(getClass().getResource("/fxml/screens/LoginScreen.fxml"));
            Stage mainStage = AppContext.mainStage;
            mainStage.getScene().setRoot(loginScreen);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    @FXML
    private void handleConfigurationButtonClick() {

    }

    @FXML
    private void handleAccessEventsButtonClick() {
        try {
            Parent eventScreen = FXMLLoader.load(getClass().getResource("/fxml/screens/EventsScreen.fxml"));
            Stage mainStage = AppContext.mainStage;
            mainStage.getScene().setRoot(eventScreen);
        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao carregar a tela de eventos");
            alert.setHeaderText(null);
            alert.setContentText("Ocorreu um erro ao tentar carregar a tela de eventos.");
            alert.showAndWait();
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
}
