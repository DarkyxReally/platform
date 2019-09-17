package com.platform.rabbitmq.service;


/**
 * 消息通知读取记录服务
 * @version: 1.0
 */
public interface MessageNoticeReadService{
    
    /**
     * 查询某人是否读取了某条通知
     * @param userNo 用户编号
     * @param idUser
     * @param idNotice
     * @return
     */
    boolean selectIsRead(int userNo, String idUser, String idNotice);

    /**
     * 更新或初始化某人读取了某条通知
     * @param userNo 用户编号
     * @param idUser 用户id
     * @param idNotice 消息id
     */
    void updateOrInitRead(int userNo, String idUser, String idNotice);
}
