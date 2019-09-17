package com.platform.system.common.trace;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 溯源配置
 * @version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@RefreshScope
@ConfigurationProperties(prefix = TraceProperties.PREFIX)
public class TraceProperties {
    
    public static final String PREFIX = "platform.trace";

    /**
     * 溯源id
     */
    public static final String TRACE_ID = "TRACE_ID";
    
    private String key = TRACE_ID;
}
