package com.platform.system.gate.exception;

/**
 * 版本受限异常
 * @version: 1.0
 */
public class VersionLimitException extends RuntimeException {

    private static final long serialVersionUID = -9190904058391154137L;

    public VersionLimitException() {
        super();
    }
    
    public VersionLimitException(String message) {
        super(message);
    }

    public VersionLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public VersionLimitException(Throwable cause) {
        super(cause);
    }

    protected VersionLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}