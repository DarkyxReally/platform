package com.platform.rabbitmq.mqmodel;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 创建定向流量账号
 * @version: 1.0
 */
@Getter
@Setter
public class CreateDirectFlowAccount {
    
    /**
     * 手机号
     */
    private String phoneNo;
    
    /**
     * 业务单号
     */
    private String businessNo;
    
    /**
     * 激活时间
     */
    private Date activateDate;
    
    /**
     * 过期时间
     */
    private Date expireDate;
}
