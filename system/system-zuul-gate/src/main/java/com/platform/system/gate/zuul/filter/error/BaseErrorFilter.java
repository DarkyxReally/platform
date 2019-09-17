package com.platform.system.gate.zuul.filter.error;

import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.rest.RestStatus;
import com.platform.system.gate.zuul.FilterConstant;
import com.platform.system.gate.zuul.filter.BaseFilter;

/**
 * 限制功能的过滤器
 * 
 * @version: 1.0
 */
@Getter
@Setter
public abstract class BaseErrorFilter extends BaseFilter {

    public BaseErrorFilter(int filterOrder) {
        super(filterOrder);
    }

    /**
     * 用于控制默认的异常处理的key
     * 当为true时,默认的异常处理器不会执行
     * 参照{@code}org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter中的SEND_ERROR_FILTER_RAN
     */
    protected static final String SEND_ERROR_FILTER_RAN = "sendErrorFilter.ran";
    
    /**
     * 发生错误的过滤器类型
     * @return
     */
    protected abstract String failedFilterTypeScope();
    
    /**
     * 是否应该处理
     * @param e
     * @return
     */
    protected abstract boolean shouldFilter(ZuulException e);
    
    @Override
    public final boolean shouldFilter(){
        // 判断：仅处理来自pre过滤器引起的异常
        RequestContext ctx = RequestContext.getCurrentContext();
        ZuulFilter failedFilter = (ZuulFilter)ctx.get(FilterConstant.FAILED_FILTER);
        if(failedFilter != null && failedFilter.filterType().equals(failedFilterTypeScope())){
            Throwable throwable = ctx.getThrowable();
            if (null != throwable){
                ZuulException zuulException = findZuulException(throwable);
                return shouldFilter(zuulException);
            }
        }
        return false;
    }

    @Override
    public final String filterType(){
        return FilterConstants.ERROR_TYPE;
    }
    
    /**
     * 输出错误
     * @param httpStatus
     * @param errorCode
     */
    protected void sendErrorResponse(HttpStatus httpStatus, RestStatus errorCode){
        // 清除异常
        clearThrowable();
        // 不再执行默认的异常处理
        disableDefaultSendErrorFilter();
        // 返回非法请求
        sendResponse(httpStatus, errorCode);
    }
    /**
     * 输出错误
     * @param httpStatus
     * @param errorCode
     * @param tips
     */
    protected void sendErrorResponse(HttpStatus httpStatus, RestStatus errorCode, String tips){
        // 清除异常
        clearThrowable();
        // 不再执行默认的异常处理
        disableDefaultSendErrorFilter();
        // 返回非法请求
        sendResponse(httpStatus, errorCode, tips);
    }
    
    /**
     * 输出错误
     * @param httpStatus
     * @param responseBody
     */
    protected void sendErrorResponse(HttpStatus httpStatus, String responseBody){
        // 清除异常
        clearThrowable();
        // 不再执行默认的异常处理
        disableDefaultSendErrorFilter();
        // 返回非法请求
        sendResponse(httpStatus, responseBody);
    }

    /**
     * 禁止默认的异常处理器进行处理
     * 
     */
    protected void disableDefaultSendErrorFilter(){
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set(SEND_ERROR_FILTER_RAN);
    }

    /**
     * 转换成zuul异常
     * 
     * @param throwable
     * @return
     */
    protected ZuulException findZuulException(Throwable throwable){
        if(throwable.getCause() instanceof ZuulRuntimeException){
            // this was a failure initiated by one of the local filters
            return (ZuulException)throwable.getCause().getCause();
        }

        if(throwable.getCause() instanceof ZuulException){
            // wrapped zuul exception
            return (ZuulException)throwable.getCause();
        }

        if(throwable instanceof ZuulException){
            // exception thrown by zuul lifecycle
            return (ZuulException)throwable;
        }

        // fallback, should never get here
        return new ZuulException(throwable, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
    }
}
