package br.upe.controllers;

import br.upe.models.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class EventListItemController {

    @FXML
    private Label tituloLabel;

    @FXML
    private Label descricaoLabel;

    @FXML
    private Button detalhesButton;

    private Event event;

    // Método para definir os dados do evento
    public void setEventData(Event event) {
        this.event = event;

        // Define os dados no layout
        tituloLabel.setText(event.getName());
        descricaoLabel.setText(event.getDescription());

        // Ação do botão "Ver detalhes"
        detalhesButton.setOnAction(e -> {
            System.out.println("Detalhes do evento: " + event.getName());
        });
    }
}