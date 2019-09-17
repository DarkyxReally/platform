package com.platform.auth.common.exception;


/**
 * 用户不存在异常
 * @version: 1.0
 */
public class NullUserException extends AuthException {

    private static final long serialVersionUID = -9216059259125081664L;

    public NullUserException(String message) {
        super(message);
    }

    public NullUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullUserException(Throwable cause) {
        super(cause);
    }

    protected NullUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
