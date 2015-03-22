package com.safira.common.exceptions;

/**
 * Created by francisco on 22/03/15.
 */
public class JPAQueryException  extends Exception {
    public JPAQueryException() {
        super("Unexpected SQL exception when executing JPA query.");
    }

    public JPAQueryException(String message) {
        super(message);
    }

    public JPAQueryException(Throwable cause) {
        super(cause);
    }

    public JPAQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}

