package com.safira.common.exceptions;

/**
 * Created by francisco on 29/03/15.
 */
public class PasswordException extends ValidatorException {
    public PasswordException(String message, String friendlyMessage) {
        super(message, friendlyMessage);
    }
}
