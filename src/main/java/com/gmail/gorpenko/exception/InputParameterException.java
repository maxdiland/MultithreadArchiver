package com.gmail.gorpenko.exception;


public class InputParameterException extends RuntimeException {
    public InputParameterException() {
    }

    public InputParameterException(String message) {
        super(message);
    }

    public InputParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputParameterException(Throwable cause) {
        super(cause);
    }
}
