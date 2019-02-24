package com.test.autosuggest.autosuggest.exceptions;

public class NotANumberException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }
    public NotANumberException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
    public NotANumberException() {
        super();
    }
}
