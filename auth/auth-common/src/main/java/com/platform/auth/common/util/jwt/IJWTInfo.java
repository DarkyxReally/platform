package com.platform.auth.common.util.jwt;

import java.util.Map;

/**
 * JWT TOKEN信息
 * @version: 1.0
 */
public interface IJWTInfo {
    
    /**
     * ID
     */
    String getId();
    
    /**
     * 根据key获取属性
     * @param key
     * @return
     */
    String get(String key);
    
    /**
     * TOKEN信息
     * @return
     */
    Map<String, String> infos();
}
