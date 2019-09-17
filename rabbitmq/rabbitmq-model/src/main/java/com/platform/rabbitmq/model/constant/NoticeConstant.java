package com.platform.rabbitmq.model.constant;

/**
 * 通知常量
 *
 * @version: 1.0
 */
public class NoticeConstant {

    /**
     * 所有平台
     */
    public static final String PLAT_FORM_ALL = "0";
    /**
     * ANDROID平台
     */
    public static final String PLATFORM_ANDROID = "1";
    /**
     * IOS平台
     */
    public static final String PLATFORM_IOS = "2";
    /**
     * winphone平台
     */
    public static final String PLATFROM_WINPHONE = "3";


    /**
     * 公告
     */
    public static final String TYPE_ANNOUNCEMENT = "0";

    /**
     * 流量通知
     */
    public final static String TYPE_RATE_NOTICE = "1";

    /**
     * 系统通知
     */
    public final static String TYPE_SYSTEM_NOTICE = "2";

    /**
     * 推送所有用户对应的用户id
     */
    public final static String ALL_USER_ID = "all";
    
    /**
     * 标签通知男:sex_1
     */
    public final static String TYPE_SEX_1 = "sex_1";

    /**
     * 标签通知女:sex_2
     */
    public final static String TYPE_SEX_2 = "sex_2";
    
    /**
     * 推送所有用户对应的用户编号
     */
    public final static int ALL_USER_NO = 0;
    
    /**
     * 推送男性用户时对应的用户编号
     */
    public final static int MAN_USER_NO = -1;
    
    /**
     * 推送女性用户时对应的用户编号
     */
    public final static int FEMALE_USER_NO = -2;
}
