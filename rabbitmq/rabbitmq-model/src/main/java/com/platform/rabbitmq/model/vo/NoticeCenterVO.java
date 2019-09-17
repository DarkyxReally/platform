package com.platform.rabbitmq.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 通知中心
 * @version: 1.0
 */
@Getter
@Setter
public class NoticeCenterVO extends MessageNoticeVO{
    
    /** 未读条数*/
    private int unReadCount;
}
