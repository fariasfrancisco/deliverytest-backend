package com.safira.common.exceptions;

/**
 * Exception thrown when failing to login.
 */
public class LoginException extends SafiraException {
    public LoginException(String message, String friendlyMessage) {
        super(message, friendlyMessage);
    }
}