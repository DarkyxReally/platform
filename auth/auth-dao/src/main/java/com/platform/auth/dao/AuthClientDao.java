package com.platform.auth.dao;

import java.util.List;

public interface AuthClientDao {

    /**
     * 查询服务所能访问的客户端
     * @param serviceId
     * @return
     */
    List<String> selectAllowedClient(String serviceId);
}