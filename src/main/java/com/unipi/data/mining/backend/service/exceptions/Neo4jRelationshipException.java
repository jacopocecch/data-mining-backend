package com.unipi.data.mining.backend.service.exceptions;

public class Neo4jRelationshipException extends RuntimeException{

    public Neo4jRelationshipException() {
        super("Entity not found!");
    }
    public Neo4jRelationshipException(String message) {
        super(message);
    }
}
