package com.platform.api.gate.exception;

/**
 * 缺少参数异常
 * @version: 1.0
 */
public class ParamsMissException extends RuntimeException {

    private static final long serialVersionUID = 4902364627542683925L;

    public ParamsMissException() {
    }
    
    public ParamsMissException(String message) {
        super(message);
    }

    public ParamsMissException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamsMissException(Throwable cause) {
        super(cause);
    }

    protected ParamsMissException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}