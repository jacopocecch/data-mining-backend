package com.unipi.data.mining.backend.service.exceptions;

public class SimilarityException extends RuntimeException{

    public SimilarityException() {
        super("There was a similarity error");
    }
    public SimilarityException(String message) {
        super(message);
    }
}
