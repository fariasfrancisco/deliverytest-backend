package com.safira.common.exceptions;

/**
 * Exception thrown when recieved serialized object contains errors.
 */
public class DeserializerException extends Exception {
    public DeserializerException() {
    }

    public DeserializerException(String message) {
        super("Unexpected exception found when trying to deserialize recieved String");
    }

    public DeserializerException(Throwable cause) {
        super(cause);
    }

    public DeserializerException(String message, Throwable cause) {
        super(message, cause);
    }
}
