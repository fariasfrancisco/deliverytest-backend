package com.safira.common.exceptions;

/**
 * Created by francisco on 29/03/15.
 */
public class NumberException extends ValidatorException {
    public NumberException(String message, String friendlyMessage) {
        super(message, friendlyMessage);
    }
}
