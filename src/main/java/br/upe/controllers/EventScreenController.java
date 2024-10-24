package br.upe.controllers;

import br.upe.models.Event;
import br.upe.services.EventService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.List;

public class EventScreenController {

    @FXML
    private ListView<Event> eventList;

    private EventService eventService;

    @FXML
    public void initialize() {
        // Inicializa o serviço de eventos
        eventService = new EventService();

        // Carrega os eventos quando a tela é aberta
        loadEvents();
    }

    @FXML
    void Atualizar(ActionEvent event) {
        // Recarrega os eventos quando o botão de atualizar é pressionado
        loadEvents();
    }

    @FXML
    void Voltar(ActionEvent event) {
        // Lógica para voltar à tela anterior, se necessário
    }

    private void loadEvents() {
        Task<List<Event>> task = eventService.getFindAllEventsTask();

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                // Obter a lista de eventos
                List<Event> events = task.getValue();
                displayEvents(events);  // Atualiza o ListView com os eventos
            }
        });

        task.setOnFailed(event -> {
            System.err.println("Erro ao carregar os eventos");
            event.getSource().getException().printStackTrace();
        });

        // Executa a tarefa em segundo plano
        new Thread(task).start();
    }

    private void displayEvents(List<Event> events) {
        // Adiciona os eventos ao ListView
        eventList.getItems().setAll(events);

        // Define a fábrica de células personalizadas
        eventList.setCellFactory(new Callback<ListView<Event>, ListCell<Event>>() {
            @Override
            public ListCell<Event> call(ListView<Event> listView) {
                return new ListCell<Event>() {
                    @Override
                    protected void updateItem(Event event, boolean empty) {
                        super.updateItem(event, empty);
                        if (empty || event == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            // Cria o layout para cada evento
                            HBox hBox = new HBox();
                            hBox.setSpacing(10);

                            // Título
                            Label tituloLabel = new Label(event.getName());
                            tituloLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

                            // Descrição
                            Label descricaoLabel = new Label(event.getDescription());
                            descricaoLabel.setStyle("-fx-font-size: 14px;");

                            // Botão "Ver Detalhes"
                            Button detalhesButton = new Button("Ver detalhes");
                            detalhesButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                            detalhesButton.setOnAction(e -> {
                                System.out.println("Detalhes do evento: " + event.getName());
                            });

                            // Adiciona os componentes ao HBox
                            hBox.getChildren().addAll(tituloLabel, descricaoLabel, detalhesButton);
                            setGraphic(hBox);
                        }
                    }
                };
            }
        });
    }
}
