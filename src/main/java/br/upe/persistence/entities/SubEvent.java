package br.upe.persistence.entities;

// JPA imports
import jakarta.persistence.Entity;
import jakarta.persistence.Basic;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

// Java imports
import java.util.UUID;

@Entity
@Table(name = "SubEvents")
public class SubEvent extends Event {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "superEventId",
        referencedColumnName = "id",
        nullable = false,
        updatable = false,
        foreignKey = @ForeignKey(name = "subEventToSuperEventFK")
    )
    private SuperEvent superEvent;

    protected SubEvent() {}

    public SubEvent(Account account, String name, SuperEvent superEvent) {
        super(account, name);
        this.superEvent = superEvent;
    }

    public SuperEvent getSuperEvent() {
        return superEvent;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        return obj instanceof SubEvent otherSubEvent
            && getId() != null
            && getId().equals(otherSubEvent.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
