package com.safira.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by francisco on 15/04/15.
 */
public class ErrorOutput {
    private String message;
    private List<ErrorDescription> errors;

    public ErrorOutput() {
        errors = new ArrayList<>();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ErrorDescription> getErrors() {
        return errors;
    }

    public String getMessage() {
        return message;
    }

    public void addError(ErrorDescription errorDescription) {
        errors.add(errorDescription);
    }

    public boolean hasErrors() {
        return !(errors.isEmpty());
    }
}
