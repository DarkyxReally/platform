package com.platform.auth.model.innermodel;

import lombok.Getter;
import lombok.Setter;

/**
 * 客户端REST操作请求日志模型
 * @version: 1.0
 */
@Getter
@Setter
public class ClientRestOperatorLogModel extends RestOperatorLogModel{

    private static final long serialVersionUID = 4299940667598245794L;
    /**
     * 客户端id
     */
    private String clientId;
}
