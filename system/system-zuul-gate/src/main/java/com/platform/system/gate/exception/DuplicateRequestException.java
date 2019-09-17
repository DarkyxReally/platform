package com.platform.system.gate.exception;

/**
 * 重复请求异常
 * @version: 1.0
 */
public class DuplicateRequestException extends RuntimeException {

    private static final long serialVersionUID = -3448500746463247175L;

    public DuplicateRequestException() {
    }
    
    public DuplicateRequestException(String message) {
        super(message);
    }

    public DuplicateRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateRequestException(Throwable cause) {
        super(cause);
    }

    protected DuplicateRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}