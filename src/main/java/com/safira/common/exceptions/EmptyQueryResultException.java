package com.safira.common.exceptions;

/**
 * Created by francisco on 22/03/15.
 */
public class EmptyQueryResultException extends SafiraException {
    public EmptyQueryResultException() {
        super("The requested query returned an empty result set.");
    }
}

