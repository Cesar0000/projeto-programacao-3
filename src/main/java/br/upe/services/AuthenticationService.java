package br.upe.services;

import br.upe.repositories.UserRepository;
import br.upe.database.Database;
import br.upe.models.User;
import br.upe.exceptions.authentication.EmailNotRegisteredException;
import br.upe.exceptions.authentication.IncorrectPasswordException;

import java.sql.Connection;
import java.util.Optional;

import javafx.concurrent.Task;

public class AuthenticationService {
    private static User loggedUser;

    public AuthenticationService() {}

    public Task<Void> getLoginTask(String email, byte[] password) {
        return new Task<Void> () {
            @Override
            protected Void call() throws Exception {
                try (Connection connection = Database.getConnection()) {
                    UserRepository userRepository = new UserRepository(connection);
                    Optional<User> user = userRepository.findByEmail(email);
                    if (user.isEmpty()) {
                        throw new EmailNotRegisteredException("The email is not registered");
                    }
                    byte[] foundUserPassword = user.get().getPassword();
                    if (foundUserPassword.equals(password)) {
                        loggedUser = user.get();
                        return null;
                    }
                    else {
                        throw new IncorrectPasswordException("The password is incorrect");
                    }
                }
            }
        };
    }

    public void logout() {
        loggedUser = null;
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}
