package br.upe.services;

import br.upe.repositories.EventRepository;

import br.upe.models.Event;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class EventService {

    public EventService() {}

    public Optional<Event> findById(int id) {
        return Optional.empty();
    }

    public List<Event> findAll() {
        return new ArrayList<Event>();
    }
}
