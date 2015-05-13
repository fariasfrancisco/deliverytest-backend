package com.safira.common.exceptions;

/**
 * Created by francisco on 22/03/15.
 */
public class InconsistencyException extends SafiraException {
    public InconsistencyException() {
        super("Inconsistency found between the recieved fields.");
    }
}
