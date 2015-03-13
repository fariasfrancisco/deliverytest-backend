package com.safira.common.exceptions;

/**
 * Exception thrown when failing to login.
 */
public class LoginException extends Exception {
    public LoginException() {
    }

    public LoginException(String message) {
        super("Unexpected exception found when trying to login");
    }

    public LoginException(Throwable cause) {
        super(cause);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}