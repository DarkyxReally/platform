package com.platform.rabbitmq.model.vo;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 通知列表
 * @version: 1.0
 */
@Getter
@Setter
public class NoticeListVO extends MessageNoticeVO {
    
    /**
     * 显示类型
     */
    private Integer showType;
    
    /**
     * Json格式的数据
     */
    private Map<String, String> extras;
    
}
