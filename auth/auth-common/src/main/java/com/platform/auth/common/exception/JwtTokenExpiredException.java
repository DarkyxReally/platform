package com.platform.auth.common.exception;

/**
 * token过期异常
 */
public class JwtTokenExpiredException extends Exception {

    private static final long serialVersionUID = 5971603515751831557L;

    public JwtTokenExpiredException(String message) {
        super(message);
    }
}
