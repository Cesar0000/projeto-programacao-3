package br.upe.repositories;

import br.upe.models.Event;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventRepository {
    private final Connection connection;

    public EventRepository(Connection connection) {
        this.connection = connection;
    }

    public Optional<Event> findById(long id) throws SQLException {
        String query = """
            SELECT *
            FROM event
            WHERE id = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            // If the event was not found return an empty Optional
            if (resultSet.next() == false) {
                return Optional.empty();
            }

            // Extract the event's informations
            Event event = new Event();
            event.setId(resultSet.getLong("id"));
            event.setName(resultSet.getString("name"));
            event.setDescription(resultSet.getString("description"));
            event.setStartDate(resultSet.getDate("start_date").toLocalDate());
            event.setEndDate(resultSet.getDate("end_date").toLocalDate());

            return Optional.of(event);
        }
    }

    public List<Event> findAll() throws SQLException {
        String query = """
            SELECT *
            FROM event
        """;

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            List<Event> events = new ArrayList<Event>();

            // Fill the list with all the events
            while (resultSet.next() != false) {
                Event event = new Event();
                event.setId(resultSet.getLong("id"));
                event.setName(resultSet.getString("name"));
                event.setDescription(resultSet.getString("description"));
                event.setStartDate(resultSet.getDate("start_date").toLocalDate());
                event.setEndDate(resultSet.getDate("end_date").toLocalDate());

                events.add(event);
            }

            return events;
        }
    }
}
