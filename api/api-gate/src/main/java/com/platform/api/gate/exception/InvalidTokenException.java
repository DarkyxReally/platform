package com.platform.api.gate.exception;

/**
 * token无效异常
 * @version: 1.0
 */
public class InvalidTokenException extends RuntimeException {

    private static final long serialVersionUID = -9190904058391154137L;

    public InvalidTokenException() {
        super();
    }
    
    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTokenException(Throwable cause) {
        super(cause);
    }

    protected InvalidTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}