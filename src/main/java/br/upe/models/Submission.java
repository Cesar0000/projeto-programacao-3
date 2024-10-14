package br.upe.models;

import java.time.Instant;

public class Submission {
    private long userId;
    private long eventId;
    private String articleName;
    private Instant timestamp;

    public Submission() {}

    public long getUserId() {
        return userId;
    }

    public Submission setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public long getEventId() {
        return eventId;
    }

    public Submission setEventId(long eventId) {
        this.eventId = eventId;
        return this;
    }

    public String getArticleName() {
        return articleName;
    }

    public Submission setArticleName(String articleName) {
        this.articleName = articleName;
        return this;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Submission setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
