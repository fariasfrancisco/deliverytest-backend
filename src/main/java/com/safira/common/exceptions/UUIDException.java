package com.safira.common.exceptions;

/**
 * Created by francisco on 29/03/15.
 */
public class UUIDException extends ValidatorException {
    public UUIDException(String message, String friendlyMessage) {
        super(message, friendlyMessage);
    }
}
