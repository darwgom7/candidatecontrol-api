package com.darwgom.candidatecontrolapi.domain.exceptions;

public class ValueAlreadyExistsException extends RuntimeException {
    public ValueAlreadyExistsException(String message) {
        super(message);
    }

}
