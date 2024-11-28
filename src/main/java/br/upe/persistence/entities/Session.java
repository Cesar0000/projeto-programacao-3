package br.upe.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Basic;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.FetchType;

import java.time.Instant;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "Sessions")
public class Session {
    public static final int NAME_MAX_LENGTH = 100;
    public static final int DESCRIPTION_MAX_LENGTH = 1_000;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "eventId",
        referencedColumnName = "id",
        nullable = false,
        foreignKey = @ForeignKey(name = "sessionToEventFK")
    )
    private Event event;

    @Basic(optional = false)
    @Column(
        name = "name",
        nullable = false,
        length = NAME_MAX_LENGTH
    )
    private String name;

    @Basic(optional = false)
    @Column(
        name = "description",
        nullable = false,
        length = DESCRIPTION_MAX_LENGTH
    )
    private String description = "";
    
    @Column(name = "startTime")
    private LocalDateTime startTime;

    @Column(name = "endTime")
    private LocalDateTime endTime;

    @Basic(optional = false)
    @Column(name = "createdAtTimestamp", nullable = false)
    private Instant createdAtTimestamp = Instant.now();

    protected Session() {}

    public Session(Event event, String name) {
        this.event = event;
        setName(name);
    }

    public UUID getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Instant getCreatedAtTimestamp() {
        return createdAtTimestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        return obj instanceof Session otherSession
            && id != null
            && id.equals(otherSession.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
