package br.upe.models;

import java.time.Instant;

public class Subscription {
    private long userId;
    private long eventId;
    private long sessionId;
    private Instant timestamp;

    public Subscription() {}

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getEventId() {
        return eventId;
    }

    public void serEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
