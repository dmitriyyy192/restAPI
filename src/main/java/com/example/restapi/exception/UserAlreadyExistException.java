package com.example.restapi.exception;

import org.springframework.http.ResponseEntity;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
