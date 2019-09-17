package com.platform.system.gate.exception;

/**
 * 请求报文解密异常
 * @version: 1.0
 */
public class RequestDecryptException extends RuntimeException {

    private static final long serialVersionUID = -9190904058391154137L;

    public RequestDecryptException() {
        super();
    }
    
    public RequestDecryptException(String message) {
        super(message);
    }

    public RequestDecryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestDecryptException(Throwable cause) {
        super(cause);
    }

    protected RequestDecryptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}