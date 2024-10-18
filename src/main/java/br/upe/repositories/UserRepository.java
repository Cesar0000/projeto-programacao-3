package br.upe.repositories;

import br.upe.models.User;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Blob;

import java.text.MessageFormat;

public class UserRepository {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public void create(User user) {
        String query = """
            INSERT INTO user (name, email, password) VALUES (?, ?, ?)
        """;

        // Validate fields
        Objects.requireNonNull(user.getName(), "The user name must not be null.");
        Objects.requireNonNull(user.getEmail(), "The user email must not be null.");
        if (user.getPassword().length == 0) {
            throw new IllegalArgumentException("The password byte array must not be empty");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            Blob passwordBlob = connection.createBlob();
            passwordBlob.setBytes(1, user.getPassword());
            preparedStatement.setBlob(3, passwordBlob);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Failed to insert new user. executeUpdate() returned 0.");
            }

            // Get the key generated by the database
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            long generatedUserId = generatedKeys.getLong(1);

            user.setId(generatedUserId);
        }
    }

    public void update(User user) {
        String baseQuery = """
            UPDATE user SET name = {0}, email = {1}, password = {3} WHERE id = ?
        """;

        Objects.requireNonNull(user.id, "The user id must not be null");

        // Decide what columns will be used in the SET clause
        String nameColumn = user.getName() != null ? "?" : "name";
        String emailColumn = user.getEmail() != null ? "?" : "email";
        String passwordColumn = user.getPassword().legth != 0 ? "?" : "password";

        // Construct the query
        String query = MessageFormat.format(baseQuery, nameColumn, emailColumn, passwordColumn);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int index = 1;

            // Fill in the prepared statement's placeholders
            if (user.getName() != null) {
                preparedStatement.setString(index++, user.getName());
            }
            if (user.getEmail() != null) {
                preparedStatement.setString(index++, user.getEmail());
            }
            if (user.getPassword().length != 0) {
                Blob passwordBlob = connection.createBlob();
                passwordBlob.setBytes(1, user.getPassword());
                preparedStatement.setBlob(index++, passwordBlob);
            }
            preparedStatement.setLong(index++, user.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("The user was not updated. executeUpdate() returned 0.");
            }
        }
    }

    public Optional<User> findById(long id) {
        String query = """
            SELECT *
            FROM user
            WHERE id = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            // If the user was not found return an empty Optional
            if (resultSet.next() == false) {
                return Optional.empty();
            }

            // Extract the user's informations
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            Blob passwordBlob = resultSet.getBlob("password");
            byte[] password = passwordBlob.getBytes(1, (int) passwordBlob.length());
            user.setPassword(password);

            return Optional.of(user);
        }
    }

    public Optional<User> findByEmail(String email) {
        String query = """
            SELECT *
            FROM user
            WHERE email = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email.toLowerCase());

            ResultSet resultSet = preparedStatement.executeQuery();

            // If the user was not found return an empty Optional
            if (resultSet.next() == false) {
                return Optional.empty();
            }

            // Extract the user's informations
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            Blob passwordBlob = resultSet.getBlob("password");
            byte[] password = passwordBlob.getBytes(1, (int) passwordBlob.length());
            user.setPassword(password);

            return Optional.of(user);
        }
    }

    public List<User> findAll() {
        String query = """
            SELECT *
            FROM user   
        """;

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            List<User> users = new ArrayList<User>();

            // Fill the list with all the users
            while (resultSet.next() != false) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                Blob passwordBlob = resultSet.getBlob("password");
                byte[] password = passwordBlob.getBytes(1, (int) passwordBlob.length());
                user.setPassword(password);

                users.add(user);
            }

            return users;
        }
    }

    public void deleteById(long id) {
        String query = """
            DELETE FROM user WHERE id = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Failed to delete user. executeUpdate() returned 0.");
            }
        }
    }
}
