package com.platform.system.gate.exception;

/**
 * 响应数据签名出现异常
 * @version: 1.0
 */
public class ResponseSignException extends RuntimeException {

    private static final long serialVersionUID = -9190904058391154137L;

    public ResponseSignException() {
        super();
    }
    
    public ResponseSignException(String message) {
        super(message);
    }

    public ResponseSignException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseSignException(Throwable cause) {
        super(cause);
    }

    protected ResponseSignException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}