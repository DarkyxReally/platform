package com.platform.auth.common.util;



/**
 * 请求日志数据保存接口
 * @version: 1.0
 */
public interface IRequestDataLogService {

    /**
     * 保存请求日志
     * @version: 1.0
     * @param request
     */
    void save(RequestData requestData);
}
