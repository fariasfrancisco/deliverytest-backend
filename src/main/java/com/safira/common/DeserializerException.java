package com.safira.common;

/**
 * Created by Francisco on 10/03/2015.
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