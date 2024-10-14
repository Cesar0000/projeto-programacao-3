package br.upe.models;

import java.time.LocalDate;

public class Event {
    private long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public Event() {}

    public long getId() {
        return id;
    }

    public Event setId(long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Event setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Event setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Event setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }
}
