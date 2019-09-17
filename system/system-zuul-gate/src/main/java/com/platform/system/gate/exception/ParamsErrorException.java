package com.platform.system.gate.exception;

/**
 * 缺少参数异常
 * @version: 1.0
 */
public class ParamsErrorException extends RuntimeException {

    private static final long serialVersionUID = 4902364627542683925L;

    public ParamsErrorException() {
    }
    
    public ParamsErrorException(String message) {
        super(message);
    }

    public ParamsErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamsErrorException(Throwable cause) {
        super(cause);
    }

    protected ParamsErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}