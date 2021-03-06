package com.gmail.gorpenko.exception;

/**
 * author Maksim Diland
 */
public class ShowHelpNeededException extends RuntimeException {
    public ShowHelpNeededException() {
    }

    public ShowHelpNeededException(String message) {
        super(message);
    }

    public ShowHelpNeededException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShowHelpNeededException(Throwable cause) {
        super(cause);
    }
}
