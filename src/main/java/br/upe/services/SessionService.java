package br.upe.services;

import br.upe.repositories.SessionRepository;

import br.upe.models.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SessionService {

    public SessionService() {}

    public Optional<Session> findById(int id) {
        return Optional.empty();
    }

    public List<Session> findAll() {
        return new ArrayList<Session>();
    }
}
