package br.upe.controllers;

import br.upe.models.Event;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class EventsListItemController {
    private Event event;

    @FXML
    private Label eventNameLabel;

    @FXML
    private Label eventDescriptionLabel;

    @FXML
    private Button seeEventDetailsButton;

    public void setButtonHandle(EventHandler<ActionEvent> handle) {
        seeEventDetailsButton.setOnAction(handle);
    }

    public void setEventDetails(Event event) {
        this.event = event;

        eventNameLabel.setText(event.getName());
        eventDescriptionLabel.setText(event.getDescription());
    }
}
