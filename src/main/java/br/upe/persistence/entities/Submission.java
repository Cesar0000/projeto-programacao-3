package br.upe.persistence.entities;

// JPA imports
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
import jakarta.persistence.GenerationType;

// Java imports
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "Submissions")
public class Submission {
    public static final int ARTICLE_NAME_MAX_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        nullable = false,
        updatable = false
    )
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "accountId",
        referencedColumnName = "id",
        nullable = false,
        updatable = false,
        foreignKey = @ForeignKey(name = "submissionToAccountFK")
    )
    private Account account;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "eventId",
        referencedColumnName = "id",
        nullable = false,
        updatable = false,
        foreignKey = @ForeignKey(name = "submissionToEventFK")
    )
    private Event event;

    @Basic(optional = false)
    @Column(
        name = "articleName",
        nullabe = false,
        length = ARTICLE_NAME_MAX_LENGTH
    )
    private String articleName;

    @Basic(optional = false)
    @Column(
        name = "createdAtTimestamp",
        nullable = false,
        updatable = false
    )
    private Instant createdAtTimestamp = Instant.now();

    protected Submission() {}

    public Submission(Account account, Event event, String articleName) {
        this.account = account;
        this.event = event;
        this.articleName = articleName;
    }

    public UUID getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Event getEvent() {
        return event;
    }

    public String getArticleName() {
        return articleName;
    }

    public Instant getCreatedAtTimestamp() {
        return createdAtTimestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        return obj instanceof Submission otherSubmission
            && id != null
            && id.equals(otherSubmission.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }    
}
