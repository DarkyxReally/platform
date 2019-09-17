package com.platform.system.common.web.response.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.json.JsonUtil;

/**
 * REST响应实体
 * 默认为响应成功状态code
 * @version: 1.0
 */
public class BaseResponse<T> implements Response {

    private static final long serialVersionUID = -7443304902819898146L;

    /**
     * 
     * [M] 状态码
     * @return
     */
    private int code;

    /**
     * [M] 状态码对应的消息
     * @return
     */
    private String msg;

    /**
     * [C] 详细信息
     * @return
     */
    private T data;
    
    public BaseResponse(){
        
    }

    public static <T> BaseResponse<T> success(){
        BaseResponse<T> baseResponse = new BaseResponse<T>();
        baseResponse.setCode(StatusCode.OK.code());
        baseResponse.setMsg(StatusCode.OK.message());
        return baseResponse;
    }
    
    public BaseResponse(T data){
        super();
        this.code = StatusCode.OK.code();
        this.msg = StatusCode.OK.message();
        this.data = data;
    }

    public int getCode(){
        return code;
    }

    public void setCode(int code){
        this.code = code;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public T getData(){
        return data;
    }

    public void setData(T data){
        this.data = data;
    }

    @Override
    public int code(){
        return this.code;
    }

    @Override
    public String msg(){
        return this.msg;
    }
    
    /**
     * 请求状态码业务成功
     * @return
     */
    @JSONField(serialize=false)
    public boolean isSuccessCode(){
        return StatusCode.OK.code() == this.code;
    }

    @Override
    public String toString(){
        return JsonUtil.toJSONString(this);
    }
}
