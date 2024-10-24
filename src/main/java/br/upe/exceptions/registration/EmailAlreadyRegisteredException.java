package br.upe.exceptions.registration;

public class EmailAlreadyRegisteredException extends Exception {

    public EmailAlreadyRegisteredException() {
        super();
    }

    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }

    public EmailAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyRegisteredException(Throwable cause) {
        super(cause);
    }
}
