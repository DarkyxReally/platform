package com.platform.api.gate.configuration.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 网关安全控制配置
 * @version: 1.0
 */
@Component
@Getter
@Setter
@ConfigurationProperties(AppVersionProperties.PREFIX)
public class AppVersionProperties {

    public static final String PREFIX = "app-version";
    
    /**
     * 最低版本
     */
    private String minVersion;

    /**
     * 是否开启APIKEY校验
     */
    private boolean enableCheckApiKey;
    
    /**
     * 需要校验安全密钥的地址
     */
    private List<String> needCheckApiKeyUrl = new ArrayList<String>();

    /**
     * 各个版本的安全密钥
     */
    private Map<String, String> versionKeyMap = new HashMap<String, String>();
}
