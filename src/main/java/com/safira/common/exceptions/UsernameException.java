package com.safira.common.exceptions;

/**
 * Created by francisco on 29/03/15.
 */
public class UsernameException extends ValidatorException {
    public UsernameException(String message, String friendlyMessage) {
        super(message, friendlyMessage);
    }
}
