package com.ga.soduko.exceptions;

public class InvalidCharacterException extends RuntimeException {
    public InvalidCharacterException(String message) {
        super(message);
    }
}
