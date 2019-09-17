package com.platform.rabbitmq.model.notice;

import java.util.Map;

/**
 * 标记一个类为可透传数据的类
 * @version: 1.0
 */
public interface IExtrasable {

    /**
     * 转换为JSON对象
     * @return
     */
    Map<String, String> toExtraMap();
}
