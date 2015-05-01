package com.safira.common;

/**
 * Created by francisco on 17/04/15.
 */
public class ErrorDescription {
    private String field;
    private String message;

    public ErrorDescription(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
