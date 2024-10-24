package br.upe.services;

import br.upe.repositories.SessionRepository;
import br.upe.models.Session;
import br.upe.database.Database;

import java.sql.Connection;
import java.util.List;

import javafx.concurrent.Task;

public class SessionService {

    public Task<List<Session>> getFindAllSessionsForEventTask(long eventId) {
        return new Task<List<Session>>() {
            @Override
            protected List<Session> call() throws Exception {
                try (Connection connection = Database.getConnection()) {
                    SessionRepository sessionRepository = new SessionRepository(connection);
                    return sessionRepository.findAllSessionsForEventId(eventId);
                }
            }
        };
    }
}
