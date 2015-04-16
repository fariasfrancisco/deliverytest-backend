package com.safira.common.exceptions;

/**
 * Created by francisco on 22/03/15.
 */
public class EmptyQueryResultException extends SafiraException {
    public EmptyQueryResultException(String message, String friendlyMessage) {
        super(message, friendlyMessage);
    }
}

