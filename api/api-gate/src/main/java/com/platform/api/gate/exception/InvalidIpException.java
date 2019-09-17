package com.platform.api.gate.exception;

/**
 * IP地址无效异常
 * @version: 1.0
 */
public class InvalidIpException extends RuntimeException {

    private static final long serialVersionUID = -9190904058391154137L;

    public InvalidIpException() {
        super();
    }
    
    public InvalidIpException(String message) {
        super(message);
    }

    public InvalidIpException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidIpException(Throwable cause) {
        super(cause);
    }

    protected InvalidIpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}