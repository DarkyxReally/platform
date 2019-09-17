package com.platform.auth.client.rpc.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.platform.auth.client.rpc.client.fallback.SystemClientFallback;
import com.platform.auth.model.model.response.system.SystemSimpleResponse;
import com.platform.system.common.web.response.entity.CollectionResponse;


/**
 * 系统数据接口客户端
 * @version: 1.0
 */
@FeignClient(name = AuthServiceId.SERVICE_ID, fallback = SystemClientFallback.class)
public interface SystemClient {

    /**
     * 接口地址前缀
     */
    String API_PATH = "/system";

    /**
     * 查询系统
     * @return
     */
    @RequestMapping(value = API_PATH + "/listAll", method = RequestMethod.GET)
    CollectionResponse<SystemSimpleResponse> listAll();
}
