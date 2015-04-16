package com.safira.common.exceptions;

/**
 * Created by francisco on 22/03/15.
 */
public class InconsistencyException extends SafiraException {
    public InconsistencyException(String message, String friendlyMesasge) {
        super(message, friendlyMesasge);
    }
}
