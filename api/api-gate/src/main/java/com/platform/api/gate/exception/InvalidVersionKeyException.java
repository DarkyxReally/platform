package com.platform.api.gate.exception;

/**
 * 版本key无效异常
 * @version: 1.0
 */
public class InvalidVersionKeyException extends RuntimeException {

    private static final long serialVersionUID = -9190904058391154137L;

    public InvalidVersionKeyException() {
        super();
    }
    
    public InvalidVersionKeyException(String message) {
        super(message);
    }

    public InvalidVersionKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVersionKeyException(Throwable cause) {
        super(cause);
    }

    protected InvalidVersionKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}