package com.unipi.data.mining.backend.service.exceptions;

public class LoginException extends RuntimeException{

    public LoginException() {
        super("There was an error during login");
    }
    public LoginException(String message) {
        super(message);
    }
}
