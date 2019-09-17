package com.platform.system.common.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@ConfigurationProperties(prefix = XssConfig.PREFIX)
public class XssConfig {
    
    public final static String PREFIX = "xss";

    /**
     * 是否开启XSS校验
     */
    private boolean enabled;

    /**
     * 忽略XSS注入的接口列表
     */
    private List<String> exempts;
    
}
