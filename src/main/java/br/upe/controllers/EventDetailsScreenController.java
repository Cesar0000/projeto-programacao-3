package br.upe.controllers;

import br.upe.models.Event;
import br.upe.userinterface.AppContext;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class EventDetailsScreenController {
    private Event event;

    @FXML
    private Button returnButton;

    @FXML
    private Label eventNameLabel;

    @FXML
    private Label eventDescriptionLabel;

    @FXML
    private Label startDateLabel;

    @FXML
    private Label endDateLabel;

    @FXML
    private Button seeSessionsButton;

    @FXML
    private void handleReturnButtonClick() {
        Parent eventsScreen;

        try {
            eventsScreen = FXMLLoader.load(getClass().getResource("/fxml/screens/EventsScreen.fxml"));
        }
        catch (IOException error) {
            error.printStackTrace();

            showError(
                "Erro ao carregar a próxima tela",
                "Um erro inesperado ocorreu durante o carregamento da tela de listagem dos eventos."
            );

            return;
        }

        Stage mainStage = AppContext.mainStage;
        mainStage.getScene().setRoot(eventsScreen);
    }

    @FXML
    private void handleSeeSessionsButtonClick() {
    }

    public void setEventDetails(Event event) {
        this.event = event;

        eventNameLabel.setText(event.getName());
        eventDescriptionLabel.setText(event.getDescription());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        startDateLabel.setText(event.getStartDate().format(formatter));
        endDateLabel.setText(event.getEndDate().format(formatter));
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
