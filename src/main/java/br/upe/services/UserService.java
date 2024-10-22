package br.upe.services;

import br.upe.repositories.UserRepository;

import br.upe.models.User;

import br.upe.database.Database;

import java.util.List;
import java.util.Optional;

import java.sql.Connection;
import java.sql.SQLException;

public class UserService {

    public UserService() {}

    public void register(User user) {
        
    }

    public void changePassword(User user, long newId) {

    }

    public Optional<User> getfindByEmailTask(String email) {
        // VALIDAÇÃO DOS DADOS
        try (Connection connection = Database.getConnection()) {
            UserRepository userRepository = new UserRepository(connection);
        }
        catch (SQLException error) {
            // LIDAR COM OS POSSÍVEIS ERROS
            // LIDAR COM InterruptedException
        }

        // RETORNAR UMA TASK DO JAVAFX
        return Optional.empty();
    }

    public void delete(User user) {

    }
}
