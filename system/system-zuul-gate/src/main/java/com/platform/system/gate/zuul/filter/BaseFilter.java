package com.platform.system.gate.zuul.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.UrlPathHelper;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.platform.system.common.json.JsonUtil;
import com.platform.system.common.rest.RestStatus;
import com.platform.system.common.web.response.entity.BaseResponse;

/**
 * 基础过滤器抽象类
 * @version: 1.0
 */
public abstract class BaseFilter extends ZuulFilter{

    /**
     * url帮助类
     */
    private static final UrlPathHelper URL_PATH_HELPER = new UrlPathHelper();

    /**
     *  过滤器顺序
     */
    private final int filterOrder;

    @Override
    public final int filterOrder(){
        return filterOrder;
    }
    
    /**
     * 构造器
     * @param filterOrder 过滤器顺序
     */
    public BaseFilter(int filterOrder) {
        super();
        this.filterOrder = filterOrder;
    }


    /**
     * 请求uri
     * @param request
     * @return
     */
    protected String getRequestUri(HttpServletRequest request){
        return URL_PATH_HELPER.getPathWithinApplication(request);
    }
    
    /**
     * 输出响应
     * @param httpStatus
     * @param statusCode
     * @param data
     */
    protected void sendResponse(HttpStatus httpStatus, RestStatus statusCode){
        this.sendResponse(httpStatus, statusCode, null);
    }

    /**
     * 输出响应
     * @param httpStatus
     * @param statusCode
     * @param data
     */
    protected <T> void sendResponse(HttpStatus httpStatus, RestStatus statusCode, String msgTip){
        this.sendResponse(httpStatus, statusCode, msgTip, null);
    }

    /**
     * 输出响应
     * @param httpStatus
     * @param statusCode
     * @param data
     */
    protected <T> void sendResponse(HttpStatus httpStatus, RestStatus statusCode, String msgTip, T data){
        BaseResponse<T> res = new BaseResponse<T>(data);
        res.setCode(statusCode.code());
        res.setMsg(null == msgTip ? statusCode.message() : msgTip);
        res.setData(data);
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseBody(JsonUtil.toJSONString(res));
        ctx.setSendZuulResponse(false);
        HttpServletResponse response = ctx.getResponse();
        response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(httpStatus.value());
    }

    /**
     * 输出响应
     * @param httpStatus
     * @param statusCode
     * @param data
     */
    protected <T> void sendResponse(HttpStatus httpStatus, RestStatus statusCode, T data){
        BaseResponse<Object> res = new BaseResponse<Object>(data);
        res.setCode(statusCode.code());
        res.setMsg(statusCode.message());
        res.setData(data);
        sendResponse(httpStatus, JsonUtil.toJSONString(res), MediaType.APPLICATION_JSON_UTF8_VALUE);
    }
    
    /**
     * 输出响应, 默认类型为json
     * @param httpStatus
     * @param responseBody
     */
    protected void sendResponse(HttpStatus httpStatus, String responseBody){
        this.sendResponse(httpStatus, responseBody, MediaType.APPLICATION_JSON_UTF8_VALUE);
    }
    
    /**
     * 输出响应
     * @param httpStatus
     * @param body
     * @param contentType
     */
    protected void sendResponse(HttpStatus httpStatus, String body, String contentType){
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseBody(body);
        ctx.setSendZuulResponse(false);
        HttpServletResponse response = ctx.getResponse();
        response.setContentType(contentType);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(httpStatus.value());
    }
    
    /**
     * 移除异常信息
     */
    protected void clearThrowable(){
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.remove("throwable");
    }
}
