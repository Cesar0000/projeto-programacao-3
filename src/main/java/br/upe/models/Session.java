package br.upe.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Session {
    private long eventId;
    private long sessionId;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Session() {}

    public long getEventId() {
        return eventId;
    }

    public Session setEventId(long eventId) {
        this.eventId = eventId;
        return this;
    }

    public long getSessionId() {
        return sessionId;
    }

    public Session setSessionId(long sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Session setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Session setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Session setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }
}
