package com.platform.system.gate.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 签名请求参数接口
 * @version: 1.0
 */
public interface IApiSignRequestParamService {

    Map<String, String> getParams(HttpServletRequest request);
}
