package com.platform.auth.service;

import java.util.List;

import com.platform.auth.model.model.response.Token;


/**
 * 认证客户端服务
 */
public interface AuthClientService {

    /**
     * 根据客户端id和密码 申请token
     * @param clientId
     * @param secret
     * @return
     * @throws Exception
     */
    public Token apply(String clientId, String secret) throws Exception;

    /**
     * 获取授权的客户端列表
     * @param serviceId
     * @param secret
     * @return
     */
    public List<String> getAllowedClient(String serviceId, String secret);

    /**
     * 获取服务授权的客户端列表
     * @param serviceId
     * @return
     */
    public List<String> getAllowedClient(String serviceId);

    /**
     * 注册客户端
     */
    public void registryClient();

    /**
     * 验证客户端
     * @param clientId
     * @param secret
     * @throws Exception
     */
    public boolean validate(String clientId, String secret) throws Exception;
}
