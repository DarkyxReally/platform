package com.platform.rabbitmq.service;

import java.util.Date;

import com.platform.rabbitmq.model.NoticeType;


/**
 * 用户最后一次读取某类通知实体服务
 * @version: 1.0
 */
public interface MessageUserLastReadService {

    /**
     * 查询最后一次读取的时间
     * @param userNo 用户编号
     * @param idUser 用户id
     * @param noticeType
     * @return
     */
    Date selectLastRead(int userNo, String idUser, NoticeType noticeType);

    /**
     * 更新或初始化最后读取时间
     * @param userNo 用户编号
     * @param idUser 用户id
     * @param noticeType
     * @return
     */
    void updateOrInitLastRead(int userNo, String idUser, NoticeType noticeType);
}
