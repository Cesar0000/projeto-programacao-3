package br.upe.controllers;

import br.upe.models.Event;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class EventsScreenController {
    @FXML
    private Button returnButton;

    @FXML
    private Button refreshButton;

    @FXML
    private ListView<HBox> eventsList;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleReturnButtonClick() {

    }

    @FXML
    private void handleRefreshButtonClick() {
        
    }

    public void setEvents(List<Event> eventsList) {

    }
}
