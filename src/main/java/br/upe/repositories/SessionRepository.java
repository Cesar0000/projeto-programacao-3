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
            FROM sessions
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
            session.setDate(resultSet.getDate("date").toLocalDate());
            session.setStartTime(resultSet.getTime("start_time").toLocalTime());
            session.setEndTime(resultSet.getTime("end_time").toLocalTime());

            return Optional.of(session);
        }
    }

    public List<Session> findAll() throws SQLException {
        String query = """
            SELECT *
            FROM sessions
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
                session.setDate(resultSet.getDate("date").toLocalDate());
                session.setStartTime(resultSet.getTime("start_time").toLocalTime());
                session.setEndTime(resultSet.getTime("end_time").toLocalTime());

                sessions.add(session);
            }

            return sessions;
        }
    }

    public List<Session> findAllSessionsForEventId(long eventId) throws SQLException {
        String query = """
            SELECT *
            FROM sessions
            WHERE event_id = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, eventId);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Session> eventSessions = new ArrayList<Session>();

            // Fill the list with all the event sessions found
            while (resultSet.next() != false) {
                Session session = new Session();
                session.setEventId(resultSet.getLong("event_id"));
                session.setSessionNumber(resultSet.getLong("session_number"));
                session.setName(resultSet.getString("name"));
                session.setDate(resultSet.getDate("date").toLocalDate());
                session.setStartTime(resultSet.getTime("start_time").toLocalTime());
                session.setEndTime(resultSet.getTime("end_time").toLocalTime());

                eventSessions.add(session);
            }

            return eventSessions;
        }
    }
}
