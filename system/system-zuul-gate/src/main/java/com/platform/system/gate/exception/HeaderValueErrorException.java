package com.platform.system.gate.exception;

/**
 * 请求头数值不正确
 * @version: 1.0
 */
public class HeaderValueErrorException extends RuntimeException {

    private static final long serialVersionUID = 4902364627542683925L;

    public HeaderValueErrorException() {
    }
    
    public HeaderValueErrorException(String message) {
        super(message);
    }

    public HeaderValueErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public HeaderValueErrorException(Throwable cause) {
        super(cause);
    }

    protected HeaderValueErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}