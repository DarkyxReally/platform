package com.platform.auth.client.rpc.client.fallback;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.platform.auth.client.rpc.client.AuthServiceId;
import com.platform.auth.client.rpc.client.IRestOperatorLogClient;
import com.platform.auth.model.innermodel.request.RestOperatorLogRequest;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.util.Shit;
import com.platform.system.common.web.response.entity.BaseResponse;


/**
 * REST请求日志失败回调
 * @version: 1.0.4
 */
@Slf4j
@Component
public class RestOperatorLogClientFallback implements IRestOperatorLogClient {

    @Value(AuthServiceId.SERVICE_ID)
    private String serviceId;

    /**
     * 请求没有响应
     */
    private void didNotGetResponse(String method){
        log.error("请求服务端:{},接口:{}失败，执行fallback方法", serviceId, method);
    }

    @Override
    public BaseResponse<Object> save(RestOperatorLogRequest logReq){
        didNotGetResponse("save(RestOperatorLogRequest logReq)");
        log.error("保存请求日志失败.");
        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

}
