package com.platform.system.common.feign;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;

/**
 * 透传请求头
 * @version: 1.0
 */
@Getter
@Setter
public class SensitiveHeaderConfig {

    @Value("${feign.sensitiveHeaders:null}")
    private String headers;
}
