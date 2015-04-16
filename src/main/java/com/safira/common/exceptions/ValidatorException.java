package com.safira.common.exceptions;

/**
 * Created by francisco on 21/03/15.
 */
public abstract class ValidatorException extends SafiraException {
    public ValidatorException(String message, String friendlyMessage) {
        super(message, friendlyMessage);
    }
}
