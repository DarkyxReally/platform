package com.platform.system.common.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Configuration
@ConfigurationProperties(prefix = WechatConfig.WECHAT_CONF, ignoreUnknownFields = true)
public class WechatConfig {
    
    /** 对应配置文件里的配置键 */
    public final static String WECHAT_CONF = "wechat";
    
    /** 图片上传最大值 */
    private String zhAppId;

    /** 小程序秘钥 */
    private String zhSecret;

    /** 小程序token地址 */
    private String wechatUrl;
    
    
    
}
