package com.safira.common;

/**
 * Created by Francisco on 09/03/2015.
 */
public class ErrorObject {
    private String message;

    public ErrorObject(String cause) {
        this.message = cause;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
