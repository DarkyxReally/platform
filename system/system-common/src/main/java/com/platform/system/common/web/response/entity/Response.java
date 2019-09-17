package com.platform.system.common.web.response.entity;

import java.io.Serializable;

/**
 * 响应数据接口
 * @version: 1.0
 */
public interface Response extends Serializable {
    
    /**
     * [M] 状态码
     * @return
     */
    int code();

    /**
     * [M] 状态码对应的消息
     * @return
     */
    String msg();
}
