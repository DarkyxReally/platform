package com.platform.system.common.config.properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 手动异常配置属性
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@ConfigurationProperties(prefix = ManualExceptionProperties.PREFIX)
public class ManualExceptionProperties {
    public static final String PREFIX = "platform.exception";

    /**
     * 是否启用随机异常
     */
    private boolean enabled;

    /**
     * 当对此数取余为0就会抛出异常
     */
    private int factor;

}
