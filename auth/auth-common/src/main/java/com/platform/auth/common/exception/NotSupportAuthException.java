package com.platform.auth.common.exception;


/**
 * 不支持的认证方式异常
 * @version: 1.0
 */
public class NotSupportAuthException extends AuthException {

    private static final long serialVersionUID = -9216059259125081664L;

    public NotSupportAuthException(String message) {
        super(message);
    }

    public NotSupportAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSupportAuthException(Throwable cause) {
        super(cause);
    }

    protected NotSupportAuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
