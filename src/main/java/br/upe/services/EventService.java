package br.upe.services;

import br.upe.repositories.EventRepository;
import br.upe.database.Database;
import br.upe.models.Event;

import java.sql.Connection;
import java.util.List;

import javafx.concurrent.Task;

public class EventService {

    public EventService() {}

    public Task<List<Event>> getFindAllEventsTask() {
        return new Task<List<Event>>() {
            @Override
            protected List<Event> call() throws Exception {
                try (Connection connection = Database.getConnection()) {
                    EventRepository eventRepository = new EventRepository(connection);
                    List<Event> events = eventRepository.findAll();
                    return events;
                }
            }
        };
    }
}
