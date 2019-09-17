package com.platform.system.common.web.response.entity;

import com.platform.system.common.json.JsonUtil;

/**
 * 响应对象
 * @version: 1.0
 * @param <T>
 */
public class ObjectResponse<T> extends BaseResponse<T> {
    
    private static final long serialVersionUID = 1862906172390850647L;

    public ObjectResponse() {
        super();
    }
    
    public ObjectResponse(T data) {
        super(data);
    }
    
    @Override
    public String toString(){
        return JsonUtil.toJSONString(this);
    }
}
