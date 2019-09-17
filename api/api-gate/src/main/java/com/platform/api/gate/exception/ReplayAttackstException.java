package com.platform.api.gate.exception;

/**
 * 重放攻击异常
 * @version: 1.0
 */
public class ReplayAttackstException extends RuntimeException {

    private static final long serialVersionUID = -9190904058391154137L;

    public ReplayAttackstException() {
        super();
    }
    
    public ReplayAttackstException(String message) {
        super(message);
    }

    public ReplayAttackstException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReplayAttackstException(Throwable cause) {
        super(cause);
    }

    protected ReplayAttackstException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}