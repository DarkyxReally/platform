package com.platform.system.common.exception;

import com.platform.system.common.rest.RestStatus;

/**
 * REST请求异常
 */
public class RestStatusException extends RuntimeException {

    private static final long serialVersionUID = -8541311111016065562L;

    private boolean printStack = true;
    /**
     * 状态码
     */
    private RestStatus restStatus;

    public RestStatusException(String message) {
        super(message);
    }

    public RestStatusException(String message, boolean printStack) {
        super(message);
        this.printStack = printStack;
        ;
    }

    public boolean isPrintStack(){
        return printStack;
    }

    public RestStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestStatusException(Throwable cause) {
        super(cause);
    }

    public RestStatusException(RestStatus restStatus) {
        super(restStatus.message());
        this.restStatus = restStatus;
    }

    public RestStatusException(RestStatus restStatus, String message) {
        super(message);
        this.restStatus = restStatus;
    }

    protected RestStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RestStatus getRestStatus(){
        return restStatus;
    }
}
