package com.platform.system.gate.exception;

/**
 * 签名无效异常
 * @version: 1.0
 */
public class InvalidSignException extends RuntimeException {

    private static final long serialVersionUID = -9190904058391154137L;

    public InvalidSignException() {
        super();
    }
    
    public InvalidSignException(String message) {
        super(message);
    }

    public InvalidSignException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSignException(Throwable cause) {
        super(cause);
    }

    protected InvalidSignException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}