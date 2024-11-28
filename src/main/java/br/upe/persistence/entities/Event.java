package br.upe.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Basic;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.time.Instant;
import java.util.UUID;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Events")
public abstract class Event {
    private static final int NAME_MAX_LENGTH = 100;
    private static final int DESCRIPTION_MAX_LENGTH = 1_000;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

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

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Basic(optional = false)
    @Column(name = "createdAtTimestamp", nullable = false)
    private Instant createdAtTimestamp = Instant.now();

    protected Event() {}

    public Event(String name) {
        setName(name);
    }

    public UUID getId() {
        return id;
    }

    public Account getAccount() {
        return account;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Instant getCreatedAtTimestamp() {
        return createdAtTimestamp;
    }
}