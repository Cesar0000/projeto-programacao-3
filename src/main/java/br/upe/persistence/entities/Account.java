package br.upe.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Basic;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.time.Instant;
import java.util.UUID;
import java.util.Objects;

@Entity
@Table(name = "Accounts")
public class Account {
    private static final int USERNAME_COLUMN_MAX_LENGTH = 20;
    private static final int EMAIL_COLUMN_MAX_LENGTH = 50;
    private static final int PASSWORD_COLUMN_MAX_LENGTH = 32;
    private static final int NAME_COLUMN_MAX_LEGNTH = 50;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Basic(optional = false)
    @Column(
        name = "username",
        nullable = false,
        unique = true,
        length = USERNAME_COLUMN_MAX_LENGTH
    )
    private String username;

    @Basic(optional = false)
    @Column(
        name = "email",
        nullable = false,
        unique = true,
        length = EMAIL_COLUMN_MAX_LENGTH
    )
    private String email;

    @Column(
        name = "password",
        nullable = false,
        length = PASSWORD_COLUMN_MAX_LENGTH
    )
    private byte[] password;

    @Basic(optional = false)
    @Column(
        name = "name",
        nullable = false,
        length = NAME_COLUMN_MAX_LEGNTH
    )
    private String name;

    @Basic(optional = false)
    @Column(name = "createdAtTimestamp", nullable = false)
    private Instant createdAtTimestamp = Instant.now();

    protected Account() {}

    public Account(String username, String email, byte[] password) {
        Objects.requireNonNull(username, "username must not be null");

        if (username.codePointCount(0, username.length()) > USERNAME_COLUMN_MAX_LENGTH) {
            throw new IllegalArgumentException("username exceeded the maximum permitted length");
        }

        this.username = username;
        setEmail(email);
        setPassword(password);
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Objects.requireNonNull(email, "email must not be null");

        if (email.codePointCount(0, email.length()) > EMAIL_COLUMN_MAX_LENGTH) {
            throw new IllegalArgumentException("email exceeded the maximum permitted length");
        }

        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        if (password.length > PASSWORD_COLUMN_MAX_LENGTH) {
            throw new IllegalArgumentException("password exceeded the maximum permitted length");
        }

        this.password = password;
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

    public Instant getCreatedAtTimestamp() {
        return createdAtTimestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        return obj instanceof Account otherAccount
            && id != null
            && id.equals(otherAccount.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
