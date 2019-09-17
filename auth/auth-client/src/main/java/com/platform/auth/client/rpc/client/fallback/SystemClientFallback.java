package com.platform.auth.client.rpc.client.fallback;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.platform.auth.client.rpc.client.AuthServiceId;
import com.platform.auth.client.rpc.client.SystemClient;
import com.platform.auth.model.model.response.system.SystemSimpleResponse;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.util.Shit;
import com.platform.system.common.web.response.entity.CollectionResponse;


@Slf4j
@Component
public class SystemClientFallback implements SystemClient {

    @Value(AuthServiceId.SERVICE_ID)
    private String serviceId;

    /**
     * 请求没有响应
     */
    private void didNotGetResponse(String method){
        log.error("请求服务端:{},接口:{}失败，执行fallback方法", serviceId, method);
    }

    @Override
    public CollectionResponse<SystemSimpleResponse> listAll(){
        didNotGetResponse("listAll");
        log.error("查询系统信息失败");
        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }
}
