package br.upe.models;

import java.time.Instant;

public class Subscription {
    private Long userId;
    private Long eventId;
    private Long sessionNumber;
    private Instant timestamp;

    public Subscription() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void serEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(Long sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
