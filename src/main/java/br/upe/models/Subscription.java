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

    public Subscription setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public long getEventId() {
        return eventId;
    }

    public Subscription serEventId(long eventId) {
        this.eventId = eventId;
        return this;
    }

    public long getSessionId() {
        return sessionId;
    }

    public Subscription setSessionId(long sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Subscription setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
