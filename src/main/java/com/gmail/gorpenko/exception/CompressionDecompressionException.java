package com.gmail.gorpenko.exception;


public class CompressionDecompressionException extends RuntimeException {
    public CompressionDecompressionException() {
    }

    public CompressionDecompressionException(String message) {
        super(message);
    }

    public CompressionDecompressionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompressionDecompressionException(Throwable cause) {
        super(cause);
    }

    public CompressionDecompressionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
