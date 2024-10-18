package br.upe.repositories;

import br.upe.models.Session;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SessionRepository {
    private final Connection connection;

    public SessionRepository(Connection connection) {
        this.connection = connection;
    }

    public Optional<Session> findById(long eventId, long sessionNumber) throws SQLException {
        String query = """
            SELECT *
            FROM session
            WHERE event_id = ? AND session_number = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, eventId);
            preparedStatement.setLong(2, sessionNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            // If the session was not found return an empty Optional
            if (resultSet.next() == false) {
                return Optional.empty();
            }

            // Extract the session's informations
            Session session = new Session();
            session.setEventId(resultSet.getLong("event_id"));
            session.setSessionNumber(resultSet.getLong("session_number"));
            session.setName(resultSet.getString("name"));
            session.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
            session.setEndTime(resultSet.getTimestamp("end_time").toLocalDateTime());

            return Optional.of(session);
        }
    }

    public List<Session> findAll() throws SQLException {
        String query = """
            SELECT *
            FROM session
        """;

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            List<Session> sessions = new ArrayList<Session>();

            // Fill the list with all the sessions
            while (resultSet.next() != false) {
                Session session = new Session();
                session.setEventId(resultSet.getLong("event_id"));
                session.setSessionNumber(resultSet.getLong("session_number"));
                session.setName(resultSet.getString("name"));
                session.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
                session.setEndTime(resultSet.getTimestamp("end_time").toLocalDateTime());

                sessions.add(session);
            }

            return sessions;
        }
    }
}
