package com.safira.common.exceptions;

/**
 * Created by francisco on 22/03/15.
 */
public class EmptyQueryResultException extends Exception {
    public EmptyQueryResultException() {
        super("Unexpected SQL exception when executing JPA query.");
    }

    public EmptyQueryResultException(String message) {
        super(message);
    }

    public EmptyQueryResultException(Throwable cause) {
        super(cause);
    }

    public EmptyQueryResultException(String message, Throwable cause) {
        super(message, cause);
    }
}

