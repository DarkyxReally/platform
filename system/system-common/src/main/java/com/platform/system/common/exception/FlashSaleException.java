package com.platform.system.common.exception;

/**
 * @version: 1.0 
*/

public class FlashSaleException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public FlashSaleException() {
        super();
    }
    public FlashSaleException(String msg) {
        super(msg);
    }
    
    public FlashSaleException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlashSaleException(Throwable cause) {
        super(cause);
    }
    
    

}
