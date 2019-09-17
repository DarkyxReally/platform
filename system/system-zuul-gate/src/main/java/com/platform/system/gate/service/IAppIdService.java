package com.platform.system.gate.service;

import javax.servlet.http.HttpServletRequest;

public interface IAppIdService {

    /**
     * 获取appId
     * 
     * @param request
     * @return
     */
    String getAppId(HttpServletRequest request);
}
