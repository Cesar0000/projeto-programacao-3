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
    public static final int USERNAME_MAX_LENGTH = 20;
    public static final int EMAIL_MAX_LENGTH = 50;
    public static final int PASSWORD_MAX_LENGTH = 32;
    public static final int NAME_MAX_LENGTH = 50;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Basic(optional = false)
    @Column(
        name = "username",
        nullable = false,
        unique = true,
        length = USERNAME_MAX_LENGTH
    )
    private String username;

    @Basic(optional = false)
    @Column(
        name = "email",
        nullable = false,
        unique = true,
        length = EMAIL_MAX_LENGTH
    )
    private String email;

    @Column(
        name = "password",
        nullable = false,
        length = PASSWORD_MAX_LENGTH
    )
    private byte[] password;

    @Basic(optional = false)
    @Column(
        name = "name",
        nullable = false,
        length = NAME_MAX_LENGTH
    )
    private String name;

    @Basic(optional = false)
    @Column(name = "createdAtTimestamp", nullable = false)
    private Instant createdAtTimestamp = Instant.now();

    protected Account() {}

    public Account(String username, String email, byte[] password) {
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
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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
