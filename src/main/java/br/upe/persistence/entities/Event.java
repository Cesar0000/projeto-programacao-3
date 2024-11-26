package br.upe.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Basic;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;
import java.util.UUID;
import java.util.Objects;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(
    name = "Events",
    uniqueConstraints = @UniqueConstraint(
        name = "uniqueEventNamePerAccount",
        columnNames = {"accountId", "name"}
    )
)
public abstract class Event {
    private static final int NAME_COLUMN_MAX_LEGNTH = 100;
    private static final int DESCRIPTION_COLUMN_MAX_LEGNTH = 1_000;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "accountId",
        referencedColumnName = "id",
        nullable = false,
        foreignKey = @ForeignKey(name = "eventToAccountFK")
    )
    private Account account;

    @Basic(optional = false)
    @Column(
        name = "name",
        nullable = false,
        length = NAME_COLUMN_MAX_LEGNTH
    )
    private String name;

    @Basic(optional = false)
    @Column(
        name = "description",
        nullable = false,
        length = DESCRIPTION_COLUMN_MAX_LEGNTH
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

    public Event(Account account, String name) {
        this.account = Objects.requireNonNull(account, "account must not be null");
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
        Objects.requireNonNull(name, "name must not be null");

        if (name.codePointCount(0, name.length()) > NAME_COLUMN_MAX_LEGNTH) {
            throw new IllegalArgumentException("name exceeded the maximum permitted length");
        }

        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        Objects.requireNonNull(description, "name must not be null");

        if (description.codePointCount(0, description.length()) > DESCRIPTION_COLUMN_MAX_LEGNTH) {
            throw new IllegalArgumentException("description exceeded the maximum permitted length");
        }

        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        Objects.requireNonNull(startDate, "startDate must not be null");

        if (endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must be <= endDate");
        }

        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        Objects.requireNonNull(endDate, "endDate must not be null");

        if (startDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("endDate must be >= startDate");
        }

        this.endDate = endDate;
    }

    public Instant getCreatedAtTimestamp() {
        return createdAtTimestamp;
    }
}
