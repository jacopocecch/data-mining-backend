package com.unipi.data.mining.backend.service.exceptions;

public class RegistrationException extends RuntimeException {

    public RegistrationException() {
        super("There was an error during registration");
    }
    public RegistrationException(String message) {
        super(message);
    }

}
