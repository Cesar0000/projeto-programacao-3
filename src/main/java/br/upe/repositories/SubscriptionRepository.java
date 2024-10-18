package br.upe.repositories;

import br.upe.models.Subscription;

import java.util.Optional;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SubscriptionRepository {
    private final Connection connection;

    public SubscriptionRepository(Connection connection) {
        this.connection = connection;
    }

    public void create(Subscription subscription) throws SQLException {
        String query = """
            INSERT INTO subscription (user_id, event_id, session_number, timestamp) VALUES (?, ?, ?, ?)
        """;

        // Validate fields
        Objects.requireNonNull(subscription.getUserId(), "The user id must not be null.");
        Objects.requireNonNull(subscription.getEventId(), "The event id must not be null.");
        Objects.requireNonNull(subscription.getSessionNumber(), "The session number must not be null.");
        Objects.requireNonNull(subscription.getTimestamp(), "The timestamp must not be null.");

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, subscription.getUserId());
            preparedStatement.setLong(2, subscription.getEventId());
            preparedStatement.setLong(3, subscription.getSessionNumber());
            preparedStatement.setTimestamp(4, Timestamp.from(subscription.getTimestamp()));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Failed to insert new subscription. executeUpdate() returned 0.");
            }
        }
    }

    public Optional<Subscription> findById(long userId, long eventId, long sessionNumber) throws SQLException {
        String query = """
            SELECT *
            FROM subscription
            WHERE user_id = ? AND event_id = ? AND session_number = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, eventId);
            preparedStatement.setLong(3, sessionNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            // If the subscription was not found return an empty Optional
            if (resultSet.next() == false) {
                return Optional.empty();
            }

            // Extract the subscription's informations
            Subscription subscription = new Subscription();
            subscription.setUserId(resultSet.getLong("user_id"));
            subscription.setEventId(resultSet.getLong("event_id"));
            subscription.setSessionNumber(resultSet.getLong("session_number"));
            subscription.setTimestamp(resultSet.getTimestamp("timestamp").toInstant());

            return Optional.of(subscription);
        }
    }

    public List<Subscription> findAllSubscriptionsForUserId(long userId) throws SQLException {
        String query = """
            SELECT *
            FROM subscription
            WHERE user_id = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Subscription> subscriptions = new ArrayList();

            // Fill the list with all the subscriptions
            while (resultSet.next() != false) {
                Subscription subscription = new Subscription();
                subscription.setUserId(resultSet.getLong("user_id"));
                subscription.setEventId(resultSet.getLong("event_id"));
                subscription.setSessionNumber(resultSet.getLong("session_number"));
                subscription.setTimestamp(resultSet.getTimestamp("timestamp").toInstant());

                subscriptions.add(subscription);
            }

            return subscriptions;
        }
    }

    public List<Subscription> findAllSubscriptionsForSessionId(long eventId, long sessionNumber) throws SQLException {
        String query = """
            SELECT *
            FROM subscription
            WHERE event_id = ? AND session_number = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, eventId);
            preparedStatement.setLong(2, sessionNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Subscription> subscriptions = new ArrayList();

            // Fill the list with all the subscriptions
            while (resultSet.next() != false) {
                Subscription subscription = new Subscription();
                subscription.setUserId(resultSet.getLong("user_id"));
                subscription.setEventId(resultSet.getLong("event_id"));
                subscription.setSessionNumber(resultSet.getLong("session_number"));
                subscription.setTimestamp(resultSet.getTimestamp("timestamp").toInstant());

                subscriptions.add(subscription);
            }

            return subscriptions;
        }
    }

    public void deleteById(long userId, long eventId, long sessionNumber) throws SQLException {
        String query = """
            DELETE FROM subscription WHERE user_id = ? AND event_id = ? AND session_number = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, eventId);
            preparedStatement.setLong(3, sessionNumber);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Failed to delete subscription. executeUpdate() returned 0.");
            }
        }
    }
}
