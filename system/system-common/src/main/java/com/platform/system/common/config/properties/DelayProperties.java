package com.platform.system.common.config.properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 延迟配置
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@ConfigurationProperties(prefix = DelayProperties.PREFIX)
public class DelayProperties {
    public static final String PREFIX = "platform.delay";

    /**
     * 延迟返回毫秒时间
     */
    private long timeInMillseconds;

}
