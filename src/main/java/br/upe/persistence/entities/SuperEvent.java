package br.upe.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

@Entity
@Table(name = "SuperEvents")
public class SuperEvent extends Event {

    @Column(name = "subscriptionsStartDateTime")
    private LocalDateTime subscriptionsStartDateTime;

    @Column(name = "subscriptionsEndDateTime")
    private LocalDateTime subscriptionsEndDateTime;

    protected SuperEvent() {}

    public SuperEvent(Account account, String name) {
        super(account, name);
    }

    public LocalDateTime getSubscriptionsStartDateTime() {
        return subscriptionsStartDateTime;
    }

    public void setSubscriptionsStartDateTime(LocalDateTime subscriptionsStartDateTime) {
        this.subscriptionsStartDateTime = subscriptionsStartDateTime;
    }

    public LocalDateTime getSubscriptionsEndDateTime() {
        return subscriptionsEndDateTime;
    }

    public void setSubscriptionsEndDateTime(LocalDateTime subscriptionsEndDateTime) {
        this.subscriptionsEndDateTime = subscriptionsEndDateTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        return obj instanceof SuperEvent otherSuperEvent
            && id != null
            && id.equals(otherSuperEvent.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
