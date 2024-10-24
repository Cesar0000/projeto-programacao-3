package br.upe.repositories;

import br.upe.models.Submission;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SubmissionRepository {
    private final Connection connection;

    public SubmissionRepository(Connection connection) {
        this.connection = connection;
    }

    public void create(Submission submission) throws SQLException {
        String query = """
            INSERT INTO submissions (event_id, user_id, article_name, created_at) VALUES (?, ?, ?, ?)
        """;

        // Validate fields
        Objects.requireNonNull(submission.getEventId(), "The event id must not be null.");
        Objects.requireNonNull(submission.getUserId(), "The user id must not be null.");
        Objects.requireNonNull(submission.getArticleName(), "The name of the article must not be null.");
        Objects.requireNonNull(submission.getCreatedAt(), "The created at timestamp must not be null.");

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, submission.getEventId());
            preparedStatement.setLong(2, submission.getUserId());
            preparedStatement.setString(3, submission.getArticleName());
            preparedStatement.setTimestamp(4, Timestamp.from(submission.getCreatedAt()));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Failed to insert new submission. executeUpdate() returned 0.");
            }
        }
    }

    public Optional<Submission> findById(long eventId, long userId) throws SQLException {
        String query = """
            SELECT user_id, event_id, article_name, created_at
            FROM submissions
            WHERE event_id = ? AND user_id = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, eventId);
            preparedStatement.setLong(2, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            // If the submission was not found return an empty Optional
            if (resultSet.next()) {
                return Optional.empty();
            }

            // Extract the submission's informations
            Submission submission = new Submission();
            submission.setEventId(resultSet.getLong("event_id"));
            submission.setUserId(resultSet.getLong("user_id"));
            submission.setArticleName(resultSet.getString("article_name"));
            submission.setCreatedAt(resultSet.getTimestamp("created_at").toInstant());

            return Optional.of(submission);
        }
    }

    public List<Submission> findAllSubmissionsForUserId(long userId) throws SQLException {
        String query = """
            SELECT user_id, event_id, article_name, created_at
            FROM submissions
            WHERE user_id = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Submission> submissions = new ArrayList<>();

            // Fill the list with all the submissions
            while (resultSet.next()) {
                Submission submission = new Submission();
                submission.setEventId(resultSet.getLong("event_id"));
                submission.setUserId(resultSet.getLong("user_id"));
                submission.setArticleName(resultSet.getString("article_name"));
                submission.setCreatedAt(resultSet.getTimestamp("created_at").toInstant());

                submissions.add(submission);
            }

            return submissions;
        }
    }

    public List<Submission> findAllSubmissionsForEventId(long eventId) throws SQLException {
        String query = """
            SELECT user_id, event_id, article_name, created_at
            FROM submissions
            WHERE event_id = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, eventId);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Submission> submissions = new ArrayList<>();

            // Fill the list with all the submissions
            while (resultSet.next()) {
                Submission submission = new Submission();
                submission.setEventId(resultSet.getLong("event_id"));
                submission.setUserId(resultSet.getLong("user_id"));
                submission.setArticleName(resultSet.getString("article_name"));
                submission.setCreatedAt(resultSet.getTimestamp("created_at").toInstant());

                submissions.add(submission);
            }

            return submissions;
        }
    }

    public void deleteById(long eventId, long userId) throws SQLException {
        String query = """
            DELETE FROM submissions WHERE event_id = ? AND user_id = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, eventId);
            preparedStatement.setLong(2, userId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Failed to delete submission. executeUpdate() returned 0.");
            }
        }
    }
}
