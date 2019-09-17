package com.platform.system.gate.service;

/**
 * 密钥接口
 * @version: 1.0
 */
public interface IAppSecretService {

    /**
     * 获取app密钥
     * 
     * @return
     */
    String getAppSecret(String appId);
}
