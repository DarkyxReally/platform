package com.platform.rabbitmq.model.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 消息通知
 * @version: 1.0
 */
@Getter
@Setter
public class MessageNoticeVO {
    
    /**
     * 主键
     */
    private String idNotice;

    /**
     * 通知类型
     */
    private Integer noticeType;
    
    /**
     * 显示类型
     */
    private Integer showType;
    
    /**
     * 场景
     */
    private Integer sence;
    
    /**
     * 内容
     */
    private String content;

    /**
     * 标题
     */
    private String title;
    
    /**
     * 通知时间
     */
    private Date dateNotice;
}
