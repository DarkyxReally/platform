package com.platform.auth.model.model.response.system;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统简要信息
 * @version: 1.0
 */
@Getter
@Setter
public class SystemSimpleResponse {

    /**
     * 系统id
     */
    private String idSystem;

    /**
     * 系统名称
     */
    private String systemName;
}
