package com.platform.auth.client.rpc.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.platform.auth.client.rpc.client.fallback.PermissionClientFallback;
import com.platform.auth.model.innermodel.response.PermissionResponse;
import com.platform.auth.model.model.PermissionInfoDTO;
import com.platform.system.common.web.response.entity.CollectionResponse;


/**
 * 系统权限数据接口客户端
 * @version: 1.0
 */
@FeignClient(name = AuthServiceId.SERVICE_ID, fallback = PermissionClientFallback.class)
public interface PermissionClient {

    /**
     * 接口地址前缀
     */
    String API_PATH = "/perms";

    /**
     * 根据系统编码获取所有权限信息
     * @return
     */
    @RequestMapping(value = API_PATH+ "/{systemCode}", method = RequestMethod.GET)
    CollectionResponse<PermissionInfoDTO> getPermissionBySystemCode(@PathVariable("systemCode") String systemCode);

    /**
     * 根据系统编码获取所有权限信息 (新)
     * @return
     */
    @RequestMapping(value = API_PATH+ "/v2/{code}", method = RequestMethod.GET)
    CollectionResponse<PermissionResponse> getPermissionByCode(@PathVariable("code") String code);
    
    /**
     * 根据用户ID和系统code获取用户权限
     * @return
     */
    @RequestMapping(value = API_PATH + "/uid/{userId}/{systemCode}", method = RequestMethod.GET)
    CollectionResponse<PermissionInfoDTO> getPermissionByUserIdAndSystemCode(@PathVariable("userId") String userId, @PathVariable("systemCode") String systemCode);

    /**
     * 根据用户ID和系统code获取用户权限 (新)
     * @return
     */
    @RequestMapping(value = API_PATH + "/v2/uid/{userId}/{code}", method = RequestMethod.GET)
    CollectionResponse<PermissionResponse> getPermissionByUserIdAndCode(@PathVariable("userId") String userId, @PathVariable("code") String code);

}
