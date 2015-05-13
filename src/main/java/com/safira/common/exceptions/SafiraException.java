package com.safira.common.exceptions;

/**
 * Created by francisco on 15/04/15.
 */
public abstract class SafiraException extends Exception {
    private String friendlyMessage;

    public SafiraException() {
        super("Failed to validate a field. Incorrect Format");
    }

    public SafiraException(String message) {
        super(message);
    }

    public SafiraException(Throwable cause) {
        super(cause);
    }

    public SafiraException(String message, Throwable cause) {
        super(message, cause);
    }

    public SafiraException(String message, String friendlyMessage) {
        super(message);
        this.friendlyMessage = friendlyMessage;
    }

    public String getFriendlyMessage() {
        return friendlyMessage;
    }
}
