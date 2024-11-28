package br.upe.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Basic;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.FetchType;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
    name = "Subscriptions",
    uniqueConstraints = @UniqueConstraint(
        name = "subscriptionsUniqueConstraint",
        columnNames = {"accountId", "superEventId"}
    )
)
public class Subscription {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "accountId",
        referencedColumnName = "id",
        nullable = false,
        foreignKey = @ForeignKey(name = "subscriptionToAccountFK")
    )
    private Account account;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "superEventId",
        referencedColumnName = "id",
        nullable = false,
        foreignKey = @ForeignKey(name = "subscriptionToSuperEventFK")
    )
    private SuperEvent superEvent;

    @Basic(optional = false)
    @Column(name = "createdAtTimestamp", nullable = false)
    private Instant createdAtTimestamp = Instant.now();

    protected Subscription() {}

    public Subscription(Account account, SuperEvent superEvent) {
        this.account = account;
        this.superEvent = superEvent;
    }

    public UUID getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public SuperEvent getSuperEvent() {
        return superEvent;
    }

    public Instant getCreatedAtTimestamp() {
        return createdAtTimestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        return obj instanceof Subscription otherSubscription
            && id != null
            && id.equals(otherSubscription.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
