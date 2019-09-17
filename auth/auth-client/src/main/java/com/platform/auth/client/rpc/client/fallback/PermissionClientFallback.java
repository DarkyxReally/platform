package com.platform.auth.client.rpc.client.fallback;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.platform.auth.client.rpc.client.AuthServiceId;
import com.platform.auth.client.rpc.client.PermissionClient;
import com.platform.auth.model.innermodel.response.PermissionResponse;
import com.platform.auth.model.model.PermissionInfoDTO;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.util.Shit;
import com.platform.system.common.web.response.entity.CollectionResponse;


/**
 * 权限服务客户端请求失败时回调处理
 * @version: 1.0
 */
@Component
@Slf4j
public class PermissionClientFallback implements PermissionClient {

    @Value(AuthServiceId.SERVICE_ID)
    private String serviceId;

    /**
     * 请求没有响应
     */
    private void didNotGetResponse(String method){
        log.error("请求服务端:{},接口:{}失败，执行fallback方法", serviceId, method);
    }

    @Override
    public CollectionResponse<PermissionInfoDTO> getPermissionBySystemCode(String systemCode){
        didNotGetResponse("getPermissionBySystemCode(String systemCode)");
        log.error("获取所有权限信息失败");
        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    @Override
    public CollectionResponse<PermissionResponse> getPermissionByCode(String code) {
        didNotGetResponse("getPermissionByCode(String code)");
        log.error("获取所有权限信息失败");
        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    @Override
    public CollectionResponse<PermissionInfoDTO> getPermissionByUserIdAndSystemCode(String userId, String systemCode){
        didNotGetResponse("getPermissionByUserIdAndSystemCode(String userId, String systemCode)");
        log.error("根据用户id:{}, 和系统code:{}, 获取用户权限失败", userId, systemCode);
        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    @Override
    public CollectionResponse<PermissionResponse> getPermissionByUserIdAndCode(String userId, String code) {
        didNotGetResponse("getPermissionByUserIdAndCode(String userId, String code)");
        log.error("根据用户id:{}, 和系统code:{}, 获取用户权限失败", userId, code);
        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }
}
