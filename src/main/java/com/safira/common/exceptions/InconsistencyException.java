package com.safira.common.exceptions;

/**
 * Created by francisco on 22/03/15.
 */
public class InconsistencyException extends Exception {
    public InconsistencyException() {
        super("An inconsistency found between the expected parameters and the recieved parameters.");
    }

    public InconsistencyException(String message) {
        super(message);
    }

    public InconsistencyException(Throwable cause) {
        super(cause);
    }

    public InconsistencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
