package br.upe.services;

import br.upe.repositories.UserRepository;
import br.upe.models.User;
import br.upe.database.Database;
import br.upe.exceptions.registration.EmailAlreadyRegisteredException;

import java.util.Optional;
import java.sql.Connection;

import javafx.concurrent.Task;

public class UserService {

    public Task<Void> getRegisterUserTask(User user) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try (Connection connection = Database.getConnection()) {
                    UserRepository userRepository = new UserRepository(connection);

                    Optional<User> foundUser = userRepository.findByEmail(user.getEmail());

                    if (!foundUser.isEmpty()) {
                        throw new EmailAlreadyRegisteredException("O email já está registrado");
                    }

                    userRepository.create(user);
                    return null;
                }
            }
        };
    }

    public boolean validateEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        return email.matches(emailPattern);
    }

    public boolean validatePassword(String password) {
        String allowedCharactersPattern = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{}|\\\\:;\"'<>,.?/]+$";

        if (!password.matches(allowedCharactersPattern)) {
            return false;
        }

        if (password.length() < 8) {
            return false;
        }

        boolean hasDigit = false;
        boolean hasLetter = false;

        for (int codePoint : password.codePoints().toArray()) {
            if (Character.isDigit(codePoint)) {
                hasDigit = true;
            }
            else if (Character.isLetter(codePoint)) {
                hasLetter = true;
            }

            if (hasDigit && hasLetter) {
                return true;
            }
        }

        return false;
    }
}
