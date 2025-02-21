package com.college.notice.exception;

public class RegistrationClosedException extends RuntimeException {
    public RegistrationClosedException(String message) {
        super(message);
    }
}
