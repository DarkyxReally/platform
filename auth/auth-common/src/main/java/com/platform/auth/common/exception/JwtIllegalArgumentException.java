package com.platform.auth.common.exception;

/**
 * token参数异常
 * @version: 1.0
 */
public class JwtIllegalArgumentException extends Exception {
    
    private static final long serialVersionUID = -3445882125636400222L;

    public JwtIllegalArgumentException(String message) {
        super(message);
    }
}
