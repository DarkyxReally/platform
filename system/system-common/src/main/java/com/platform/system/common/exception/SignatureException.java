package com.platform.system.common.exception;

/**
 * 签名异常
 * @version: 1.0
 */
public class SignatureException extends RuntimeException {

    private static final long serialVersionUID = 8009088829199901509L;

    public SignatureException() {
        super();
    }

    public SignatureException(String message) {
        super(message);
    }

    public SignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignatureException(Throwable cause) {
        super(cause);
    }

    protected SignatureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
