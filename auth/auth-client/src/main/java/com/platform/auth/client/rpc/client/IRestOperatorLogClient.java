package com.platform.auth.client.rpc.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.platform.auth.client.rpc.client.fallback.RestOperatorLogClientFallback;
import com.platform.auth.model.innermodel.request.RestOperatorLogRequest;
import com.platform.system.common.web.response.entity.BaseResponse;


/**
 * REST1请求日志客户端
 * @version: 1.0
 */
@FeignClient(name = AuthServiceId.SERVICE_ID, configuration = {}, fallback = RestOperatorLogClientFallback.class)
public interface IRestOperatorLogClient {

    /**
     * 接口地址前缀
     */
    String API_PATH = "/inner/rest/operator/log";

    /**
     * 保存记录
     * @param serviceId
     * @param secret
     * @return
     */
    @RequestMapping(value = API_PATH + "", method = RequestMethod.POST)
    BaseResponse<Object> save(RestOperatorLogRequest logReq);
}
