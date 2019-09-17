package com.platform.auth.common.exception;

/**
 * token签名异常
 * @version: 1.0
 */
public class JwtSignatureException extends Exception {
    
    private static final long serialVersionUID = -3062201578787493685L;

    public JwtSignatureException(String message) {
        super(message);
    }
}
