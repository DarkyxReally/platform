package com.platform.system.common.util;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.servlet.http.HttpServletRequest;

import com.platform.system.common.exception.RestStatusException;
import com.platform.system.common.rest.RestStatus;
import com.platform.system.common.web.ServletContextHolder;
import com.platform.system.common.web.response.entity.ErrorResponse;


/**
 * 抛出异常
 * @version: 1.0
 */
public final class Shit {

    private Shit() {
    }

    /**
     * 抛出具体的{@code RestStatus}异常
     *
     * @param status  自定义异常实体
     * @param msg 自定义的错误内容
     */
    public static void fatal(RestStatus status, String msg) {
        checkNotNull(status);
        final ErrorResponse<Object> entity = new ErrorResponse<Object>(status, msg);
        // put it into request, details entity by Rest Status's name
        String errorCode = String.valueOf(status.code());
        bindStatusCodesInRequestScope(errorCode, entity);
        throw new RestStatusException(errorCode, status.isLogErrorStack());
    }
    
    
    /**
     * 抛出具体的{@code RestStatus}异常
     *
     * @param status  自定义异常实体
     */
    public static void fatal(RestStatus status) {
        checkNotNull(status);
        final ErrorResponse<Object> entity = new ErrorResponse<Object>(status);
        // put it into request, details entity by Rest Status's name
        String errorCode = String.valueOf(status.code());
        bindStatusCodesInRequestScope(errorCode, entity);
        throw new RestStatusException(errorCode, status.isLogErrorStack());
    }
    
    /**
     * 抛出具体的{@code RestStatus}异常
     * @param status  自定义异常实体
     * @param details 额外添加至details字段中的任意实体, 最终会被解析成JSON
     * @Deprecated 该方法的 details会设置到BaseResponse<T> 中的setData(T) 方法中, 请自行确保调用时,接收数据的对象是否匹配
     */
    @Deprecated
    public static void fatal(RestStatus status, Object... details) {
        checkNotNull(status);
        final ErrorResponse<Object> entity = new ErrorResponse<Object>(status);
        // inject details
        if (details.length > 0) {
            if (details.length == 1){
                Object object = details[0];
                if (object instanceof String){
                    // 只有一个数据并且是字符串型的, 填充至msg字段中
                    entity.setMsg((String)object);
                }else{
                    entity.setData(object);
                }
            }
            else{
                entity.setData(details);
            }
        }
        // put it into request, details entity by Rest Status's name
        String errorCode = String.valueOf(status.code());
        bindStatusCodesInRequestScope(errorCode, entity);
        throw new RestStatusException(errorCode, status.isLogErrorStack());
    }

    /**
     * 当前请求绑定状态码
     * @param key
     * @param entity
     */
    private static void bindStatusCodesInRequestScope(String key, ErrorResponse<Object> entity) {
        checkNotNull(entity);
        checkNotNull(key);
        HttpServletRequest request = ServletContextHolder.getRequest();
        if (null != request){
            request.setAttribute(key, entity);
        }
    }
}