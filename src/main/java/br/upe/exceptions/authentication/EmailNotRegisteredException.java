package br.upe.exceptions.authentication;

public class EmailNotRegisteredException extends Exception {

    public EmailNotRegisteredException() {
        super();
    }

    public EmailNotRegisteredException(String message) {
        super(message);
    }

    public EmailNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailNotRegisteredException(Throwable cause) {
        super(cause);
    }
}
