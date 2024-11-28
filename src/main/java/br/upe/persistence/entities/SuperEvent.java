package br.upe.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.FetchType;

import java.time.LocalDateTime;

@Entity
@Table(name = "SuperEvents")
public class SuperEvent extends Event {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "accountId",
        referencedColumnName = "id",
        nullable = false,
        foreignKey = @ForeignKey(name = "superEventToAccountFK")
    )
    private Account account;

    @Column(name = "subscriptionsStartTime")
    private LocalDateTime subscriptionsStartTime;

    @Column(name = "subscriptionsEndTime")
    private LocalDateTime subscriptionsEndTime;

    protected SuperEvent() {}

    public SuperEvent(String name, Account account) {
        super(name);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public LocalDateTime getSubscriptionsStartTime() {
        return subscriptionsStartTime;
    }

    public void setSubscriptionsStartTime(LocalDateTime subscriptionsStartTime) {
        this.subscriptionsStartTime = subscriptionsStartTime;
    }

    public LocalDateTime getSubscriptionsEndTime() {
        return subscriptionsEndTime;
    }

    public void setSubscriptionsEndTime(LocalDateTime subscriptionsEndTime) {
        this.subscriptionsEndTime = subscriptionsEndTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        return obj instanceof SuperEvent otherSuperEvent
            && getId() != null
            && getId().equals(otherSuperEvent.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
