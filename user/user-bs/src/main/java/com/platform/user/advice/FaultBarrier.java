package com.platform.user.advice;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.google.common.collect.ImmutableMap;
import com.platform.system.common.constant.RequestAttributeConst;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.exception.IllegalValidateException;
import com.platform.system.common.exception.RestStatusException;
import com.platform.system.common.rest.RestStatus;
import com.platform.system.common.web.annotations.RequestLogging;
import com.platform.system.common.web.response.entity.ErrorResponse;

/**
 * 异常拦截
 * @version: 1.0
 */
@ControllerAdvice
public class FaultBarrier {
    private static final Logger LOGGER = LoggerFactory.getLogger(FaultBarrier.class);
    private static final ImmutableMap<Class<? extends Throwable>, RestStatus> EXCEPTION_MAPPINGS;

    static {
        final ImmutableMap.Builder<Class<? extends Throwable>, RestStatus> builder = ImmutableMap.builder();
        // SpringMVC中参数类型转换异常，常见于String找不到对应的ENUM而抛出的异常
        builder.put(MethodArgumentTypeMismatchException.class, StatusCode.INVALID_PARAMS_CONVERSION);
        builder.put(UnsatisfiedServletRequestParameterException.class, StatusCode.INVALID_PARAMS_CONVERSION);
        // HTTP 页面不存在
        builder.put(NoHandlerFoundException.class, StatusCode.PAGE_NOT_FOUND);
        // HTTP Request Method不存在
        builder.put(HttpRequestMethodNotSupportedException.class, StatusCode.REQUEST_METHOD_NOT_SUPPORTED);
        // 请求内容类型不支持
        builder.put(HttpMediaTypeNotAcceptableException.class, StatusCode.REQUEST_CONTENT_TYPE_NOT_SUPPORTED);
        // 要求有RequestBody的地方却传入了NULL
        builder.put(HttpMessageNotReadableException.class, StatusCode.HTTP_MESSAGE_NOT_READABLE);
        // 消息不可写
        builder.put(HttpMessageNotWritableException.class, StatusCode.HTTP_MESSAGE_NOT_WRITABLE);
        // HTTP Request contentType有误
        builder.put(HttpMediaTypeNotSupportedException.class, StatusCode.REQUEST_CONTENT_TYPE_NOT_SUPPORTED);
        // 通常是操作过快导致DuplicateKey
        builder.put(DuplicateKeyException.class, StatusCode.DUPLICATE_KEY);
        // 缺少必须的参数
        builder.put(MissingServletRequestParameterException.class, StatusCode.INVALID_MODEL_FIELDS);
        // 类型转换异常
        builder.put(ClassCastException.class, StatusCode.SERVER_CLASSCAST_EXCEPTION);
        builder.put(ConversionNotSupportedException.class, StatusCode.SERVER_CLASSCAST_EXCEPTION);
        // 空指针
        builder.put(NullPointerException.class, StatusCode.SERVER_NULL_POINTER_EXCEPTION);
        // 数组越界
        builder.put(IndexOutOfBoundsException.class, StatusCode.SERVER_INDEX_OUT_OF_BOUNDS_EXCEPTION);
        // 没有方法
        builder.put(NoSuchMethodException.class, StatusCode.SERVER_NO_SUCH_METHOD_EXCEPTION);
        // IOException
        builder.put(IOException.class, StatusCode.SERVER_IO_EXCEPTION_EXCEPTION);
        // RuntimeException
        builder.put(RuntimeException.class, StatusCode.SERVER_RUNTIME_EXCEPTION);
        // 其他未被发现的异常
        builder.put(Exception.class, StatusCode.SERVER_UNKNOWN_ERROR);
        
        EXCEPTION_MAPPINGS = builder.build();
    }

    /**
     * <strong>Request域取出对应错误信息</strong>, 封装成实体ErrorEntity后转换成JSON输出
     *
     * @param e       {@code StatusCode}异常
     * @param request HttpServletRequest
     * @return ErrorEntity
     * @see ErrorEntity
     * @see StatusCode
     */
    @ResponseBody
    @RequestLogging
    @ExceptionHandler(RestStatusException.class)
    public Object restStatusException(RestStatusException e, HttpServletRequest request) {
        RestStatus restStatus = e.getRestStatus();
        if (null == restStatus){
            boolean printStack = e.isPrintStack();
            if (printStack){
                Object requestId = request.getAttribute(RequestAttributeConst.REQUEST_ID);
                LOGGER.error("request id: " + requestId + "\r\nexception: "+e.getMessage(), e);
            }
            else{
                LOGGER.info("request id: {}\r\nexception: {}:{}", request.getAttribute(RequestAttributeConst.REQUEST_ID), e.getMessage(), request.getAttribute(e.getMessage()));
            }
            // 取出存储在Shift设定在Request Scope中的ErrorEntity
            return request.getAttribute(e.getMessage());
        }else{
            // 指定的错误码
            return new ErrorResponse<Object>(restStatus, e.getMessage());
        }
    }

    /**
     * <strong>Request域取出对应错误信息</strong>, 封装成实体ErrorEntity后转换成JSON输出
     *
     * @param e       {@code IllegalValidateException}异常
     * @param request HttpServletRequest
     * @return ErrorEntity
     * @see ErrorEntity
     */
    @ResponseBody
    @RequestLogging
    @ExceptionHandler(IllegalValidateException.class)
    public Object illegalValidateException(IllegalValidateException e, HttpServletRequest request) {
        // Object requestId = request.getAttribute(RequestAttributeConst.REQUEST_ID);
        // 校验失败的异常不打印
        // LOGGER.error("request id: " + requestId + "\r\nexception: "+e.getMessage(), e);
        // 取出存储在Request域中的Map
        return request.getAttribute(e.getMessage());
    }

    /**
     * 系统异常
     * @param e
     * @param request
     * @return
     */
    @ResponseBody
    @RequestLogging
    @ExceptionHandler(Exception.class)
    public ErrorResponse<Object> exception(Exception e, HttpServletRequest request) {
        final RestStatus status = EXCEPTION_MAPPINGS.get(e.getClass());
        final ErrorResponse<Object> error;
        if (status != null) {
            if (status.isLogErrorStack()){
                Object requestId = request.getAttribute(RequestAttributeConst.REQUEST_ID);
                LOGGER.error("request id: " + requestId + "\r\nexception: "+e.getMessage(), e);
            }
            else{
                LOGGER.error("request id: {}\r\nexception: {}, {}", request.getAttribute(RequestAttributeConst.REQUEST_ID), status.message(), e.getMessage());
            }
            error = new ErrorResponse<Object>(status);
        } else {
            Object requestId = request.getAttribute(RequestAttributeConst.REQUEST_ID);
            LOGGER.error("request id: " + requestId + "\r\nexception: "+e.getMessage(), e);
            error = new ErrorResponse<Object>(StatusCode.SERVER_UNKNOWN_ERROR);
        }
        return error;
    }
}
