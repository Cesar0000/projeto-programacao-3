package br.upe.services;

import br.upe.models.User;

import java.util.Optional;

public class AuthenticationService {
    private static User loggegUser;

    public AuthenticationService() {}

    public void login(String email, byte[] password) {

    }

    public void logout() {

    }

    public Optional<User> getLoggedUser() {
        return Optional.empty();
    }
}
