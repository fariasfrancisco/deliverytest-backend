package com.safira.common;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by francisco on 15/04/15.
 */
@Component
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

    public void flush() {
        message = "";
        errors = new ArrayList<>();
    }

    public void addError(String header, String field, String message) {
        message = header;
        ErrorDescription error = new ErrorDescription(field, message);
        errors.add(error);
    }
}
