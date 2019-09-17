package com.platform.system.gate.exception;

/**
 * IP地址受限异常
 * @version: 1.0
 */
public class IpLimitException extends RuntimeException {

    private static final long serialVersionUID = -9190904058391154137L;

    public IpLimitException() {
        super();
    }
    
    public IpLimitException(String message) {
        super(message);
    }

    public IpLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public IpLimitException(Throwable cause) {
        super(cause);
    }

    protected IpLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}