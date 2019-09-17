package com.platform.rabbitmq.model.vo;

import lombok.Getter;
import lombok.Setter;

import com.alibaba.fastjson.JSONObject;

/**
 * 通知详情
 * @version: 1.0
 */
@Getter
@Setter
public class NoticeDetailVO extends MessageNoticeVO {
    
    /**
     * Json格式的数据
     */
    private JSONObject extras;
}
