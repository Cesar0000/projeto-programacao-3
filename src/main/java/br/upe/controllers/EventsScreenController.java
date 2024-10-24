package br.upe.controllers;

import br.upe.models.Event;
import br.upe.services.EventService;
import br.upe.userInterface.AppContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class EventsScreenController {

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
        loadEvents();
    }

    @FXML
    void Voltar(ActionEvent event) {
        try {
            // Carrega o FXML da tela inicial (HomeScreen)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/screens/HomeScreen.fxml"));
            Parent homeScreen = loader.load();

            // Obtém a cena do stage principal e define a nova tela como raiz
            Stage mainStage = AppContext.mainStage;
            mainStage.getScene().setRoot(homeScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        new Thread(task).start();
    }

    private void displayEvents(List<Event> events) {
        // Adiciona os eventos ao ListView
        eventList.getItems().setAll(events);

        // Define uma fábrica de células personalizada carregando o layout de cada célula a partir do FXML
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
                            try {
                                // Carrega o layout da célula do evento a partir do FXML
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EventItem.fxml"));
                                HBox eventBox = loader.load();

                                // Obtém o controlador da célula
                                EventListItemController itemController = loader.getController();

                                // Define os dados do evento no controlador
                                itemController.setEventData(event);

                                // Define o layout carregado como gráfico da célula
                                setGraphic(eventBox);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
            }
        });
    }
}
