package com.platform.system.common.web.response.entity;

import com.platform.system.common.json.JsonUtil;
import com.platform.system.common.rest.RestStatus;

/**
 * 响应错误实体
 * @version: 1.0
 */
public class ErrorResponse<T> extends BaseResponse<T> {

    private static final long serialVersionUID = 3550224421750657701L;
    
    public ErrorResponse(RestStatus statusCodes) {
        super();
        setCode(statusCodes.code());
        setMsg(statusCodes.message());
    }
    
    public ErrorResponse(RestStatus statusCodes, String msg) {
        super();
        setCode(statusCodes.code());
        setMsg(msg == null ? statusCodes.message(): msg);
    }

    public ErrorResponse(RestStatus statusCodes, T data) {
        super();
        setCode(statusCodes.code());
        setMsg(statusCodes.message());
        setData(data);
    }
    
    public ErrorResponse(RestStatus statusCodes, String msg, T data) {
        super();
        setCode(statusCodes.code());
        setMsg(msg == null ? statusCodes.message(): msg);
        setData(data);
    }

    @Override
    public String toString(){
        return JsonUtil.toJSONString(this);
    }
}
