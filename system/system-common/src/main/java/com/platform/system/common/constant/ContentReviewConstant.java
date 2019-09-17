package com.platform.system.common.constant;

/**
 * 网易审核 常量类
 * @version: 1.0
 */
public class ContentReviewConstant {
    
    /*** 审核结果通过  */
    public static final String NETEASE_CHECK_YES = "0";
    
    /*** 审核结果可疑 */
    public static final String NETEASE_CHECK_SUSPECT = "1";
    
    /*** 审核结果不通过 */
    public static final String NETEASE_CHECK_NO = "2";
    
    /** 人工审核状态：1待人工审核  */
    public static final String MAN_CHECK_STATUS_1 = "1";
    
    /** 人工审核状态：2人工审核为正常  */
    public static final String MAN_CHECK_STATUS_2 = "2";
    
    /** 人工审核状态：3人工审核为违规  */
    public static final String MAN_CHECK_STATUS_3 = "3";
    
    /** 人工审核状态：4人工删除  */
    public static final String MAN_CHECK_STATUS_4 = "4";
    
    /** 审核图片处理，宽度固定300，质量70% ，避免图片过大耗费时间和资源*/
    public static final String CHECK_IMG_STYLE = "?imageView2/0/q/70/w/500";
    
    /** 检查类型 -1:未知 */
    public static final String CHECK_TYPE_UNKNOWN = "-1";
    
    /** 检查类型 1:文本 */
    public static final String CHECK_TYPE_1 = "1";
    
    /** 检查类型 2:图片 */
    public static final String CHECK_TYPE_2 = "2";
    
    /** 检查类型 3:语音 */
    public static final String CHECK_TYPE_3 = "3";
    
    /** 检查类型 4:视频 */
    public static final String CHECK_TYPE_4 = "4";
    
    /** 检查类型 5:其他*/
    public static final String CHECK_TYPE_5 = "5";
    
    /** 审核状态 -1:待审核 */
    public static final String STATUS_M1 = "-1";
    
    /** 审核状态 0:正常 */
    public static final String STATUS_0 = "0";
    
    /** 审核状态1:可疑 */
    public static final String STATUS_1 = "1";
    
    /** 审核状态 2:违规 */
    public static final String STATUS_2 = "2";
    
    /** 第三方内容类型  1:昵称 */
    public static final String CONTENT_TYPE_1 = "1";
    
    /** 第三方内容类型  2:头像 */
    public static final String CONTENT_TYPE_2 = "2";
    
    /** 第三方内容类型  3:相册 */
    public static final String CONTENT_TYPE_3 = "3";
    
    /** 第三方内容类型  4:动态 */
    public static final String CONTENT_TYPE_4 = "4";
    
    /** 第三方内容类型  5:评论 */
    public static final String CONTENT_TYPE_5 = "5";
}
