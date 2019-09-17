package com.platform.system.gate.zuul.biz;

/**
 * 响应数据加密异常
 * @version: 1.0
 */
public class ResponseEncryptException extends RuntimeException {

    private static final long serialVersionUID = -9190904058391154137L;

    public ResponseEncryptException() {
        super();
    }
    
    public ResponseEncryptException(String message) {
        super(message);
    }

    public ResponseEncryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseEncryptException(Throwable cause) {
        super(cause);
    }

    protected ResponseEncryptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}