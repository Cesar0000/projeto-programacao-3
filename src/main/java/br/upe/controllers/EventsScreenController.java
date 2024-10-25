package br.upe.controllers;

import br.upe.models.Event;
import br.upe.userinterface.AppContext;
import br.upe.services.EventService;
import br.upe.controllers.EventsListItemController;
import br.upe.controllers.EventDetailsScreenController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class EventsScreenController {
    private EventService eventService = new EventService();
    private Task<List<Event>> databaseTask;
    private Task<Void> renderingTask;
    private boolean tasksCancelled = false;

    @FXML
    private Button returnButton;

    @FXML
    private Button refreshButton;

    @FXML
    private ListView<HBox> eventsListView;

    @FXML
    private void initialize() {
        loadEvents();
    }

    @FXML
    private void handleReturnButtonClick() {
        Parent homeScreen;

        try {
            homeScreen = FXMLLoader.load(getClass().getResource("/fxml/screens/HomeScreen.fxml"));
        }
        catch (IOException error) {
            error.printStackTrace();

            showError(
                "Erro ao carregar a próxima tela",
                "Um erro inesperado ocorreu durante o carregamento da tela inicial."
            );

            return;
        }

        cancelTasks();
        Stage mainStage = AppContext.mainStage;
        mainStage.getScene().setRoot(homeScreen);
    }

    @FXML
    private void handleRefreshButtonClick() {
        loadEvents();
    }

    private void cancelTasks() {
        if (databaseTask != null) {
            databaseTask.cancel();
        }
        if (renderingTask != null) {
            renderingTask.cancel();
        }
        tasksCancelled = true;
    }

    private void loadEvents() {
        refreshButton.setDisable(true);

        Task<List<Event>> findAllEventsTask = eventService.getFindAllEventsTask();

        findAllEventsTask.setOnSucceeded(e -> {
            List<Event> eventsList = findAllEventsTask.getValue();
            renderEvents(eventsList);
        });

        findAllEventsTask.setOnFailed(e -> {
            Throwable error = findAllEventsTask.getException();
            if (error != null) {
                error.printStackTrace();
            }
            showError("Erro ao carregar eventos", "Ocorreu um erro ao acessar o banco de dados.");
            refreshButton.setDisable(false);
        });

        this.databaseTask = findAllEventsTask;
        ExecutorService threadPool = AppContext.threadPool;
        threadPool.submit(findAllEventsTask);
    }

    private void renderEvents(List<Event> eventsList) {
        eventsListView.getItems().clear();

        Task<Void> renderEventsTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (tasksCancelled) {
                    cancel();
                }

                for (Event event : eventsList) {
                    if (isCancelled()) {
                        break;
                    }

                    FXMLLoader screenLoader = new FXMLLoader(getClass().getResource("/fxml/uiComponents/EventsListItem.fxml"));
                    HBox listItem = screenLoader.load();
    
                    EventsListItemController listItemController = screenLoader.getController();
                    
                    listItemController.setEventDetails(event);
                    listItemController.setButtonHandle(e -> loadEventDetailsScreen(event));

                    Platform.runLater(() -> {
                        eventsListView.getItems().add(listItem);
                    });
                }
                return null;
            }
        };

        renderEventsTask.setOnSucceeded(e -> {
            refreshButton.setDisable(false);
        });

        renderEventsTask.setOnFailed(e -> {
            Throwable error = renderEventsTask.getException();
            if (error != null) {
                error.printStackTrace();
            }
            refreshButton.setDisable(false);
        });

        this.renderingTask = renderEventsTask;
        ExecutorService threadPool = AppContext.threadPool;
        threadPool.submit(renderEventsTask);
    }

    private void loadEventDetailsScreen(Event event) {
        FXMLLoader screenLoader = new FXMLLoader(getClass().getResource("/fxml/screens/EventDetailsScreen.fxml"));
        Parent eventDetailsScreen;

        try {
            eventDetailsScreen = screenLoader.load();
        }
        catch (IOException error) {
            error.printStackTrace();

            showError(
                "Erro ao carregar a próxima tela",
                "Um erro inesperado ocorreu durante o carregamento da tela com os detalhes do evento."
            );

            return;
        }

        cancelTasks();

        EventDetailsScreenController eventDetailsScreenController = screenLoader.getController();
        eventDetailsScreenController.setEventDetails(event);

        Stage mainStage = AppContext.mainStage;
        mainStage.getScene().setRoot(eventDetailsScreen);
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
