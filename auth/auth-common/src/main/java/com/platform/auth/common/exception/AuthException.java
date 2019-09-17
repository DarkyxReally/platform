package com.platform.auth.common.exception;


/**
 * 认证异常
 * @version: 1.0
 */
public class AuthException extends RuntimeException {

    private static final long serialVersionUID = -9216059259125081664L;

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    protected AuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
