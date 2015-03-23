package com.safira.common.exceptions;

/**
 * Created by francisco on 21/03/15.
 */
public class ValidatorException extends Exception {
    public ValidatorException() {
        super("Failed to validate a field. Incorrect Format");
    }

    public ValidatorException(String message) {
        super(message);
    }

    public ValidatorException(Throwable cause) {
        super(cause);
    }

    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
