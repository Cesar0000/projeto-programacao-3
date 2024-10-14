package br.upe.models;

public class User {
    private long id;
    private String name;
    private String email;
    private byte[] password;

    public User() {}

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public byte[] getPassword() {
        return password;
    }

    public User setPassword(byte[] password) {
        this.password = password;
        return this;
    }
}
