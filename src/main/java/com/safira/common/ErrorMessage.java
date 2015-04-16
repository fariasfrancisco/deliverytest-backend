package com.safira.common;

import com.safira.common.exceptions.SafiraException;

/**
 * Created by francisco on 15/04/15.
 */
public class ErrorMessage {
    private String error;
    private String message;

    public ErrorMessage(Exception e) {
        this(e.getMessage(), "Please fix the error and try again later.");
    }

    public ErrorMessage(SafiraException e) {
        this(e.getMessage(), e.getFriendlyMessage());
    }

    private ErrorMessage(String error, String message) {
        this.error = error;
        this.message = message;
    }


}
