package br.upe.services;

import br.upe.repositories.SessionRepository;
import br.upe.models.Session;
import br.upe.database.Database;

import java.sql.Connection;
import java.util.List;

import javafx.concurrent.Task;

public class SessionService {

    public SessionService() {}

    public Task<List<Session>> getFindAllSessionsForEventTask(long eventId) {
        return new Task<List<Session>>() {
            @Override
            protected Void call() throws Exception {
                try (Connection connection = Database.getConnection()) {
                    SessionRepository sessionRepository = new SessionRepository(connection);
                    List<Session> eventSessions = sessionRepository.findAllSessionsForEventId(eventId);
                    return eventSessions;
                }
            }
        };
    }
}
