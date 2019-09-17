package com.platform.system.gate.exception;

/**
 * 权限无效引起的异常
 * @version: 1.0
 */
public class InvalidPermissionException extends RuntimeException {

    private static final long serialVersionUID = -6764517453674192098L;

    public InvalidPermissionException() {
        super();
    }

    public InvalidPermissionException(String message) {
        super(message);
    }

    public InvalidPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPermissionException(Throwable cause) {
        super(cause);
    }

    protected InvalidPermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
