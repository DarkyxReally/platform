package com.platform.system.common.constant;

import com.platform.system.common.util.PatternCacheUtils;

/**
 * <p>正则表达式集合</p>
 * @version 1.0
 **/
public class RegexConstants {

    public static void main(String[] args) {
        String testStr = "91370982795348165Q";
        System.out.println(PatternCacheUtils.getPattern(UNIFIED_SOCIAL_CREDIT_CODE).matcher(testStr).matches());
    }

    /**
     * 密码：6-16位包含大小写字母、数字
     */
    public static final String PASSWORD_6_16 = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,16}$";
    /**
     * 邮箱
     */
    public static final String EMAIL = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
    /**
     * 座机
     */
    public static final String TELEPHONE = "0\\d{2,3}-\\d{7,8}";
    /**
     * 手机
     */
    public static final String PHONE = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|17[0|1|2|3|6]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
    /**
     * 18位身份证
     */
    public static final String IDCARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
    /**
     * IPV4校验
     */
    public static final String IPV4 = "\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";
    /**
     * IPV6校验
     */
    public static final String IPV6 = "(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))";
    /**
     * url地址
     */
    public static final String URL = "^(http|https|ftp):\\/\\/([a-zA-Z0-9\\.-]+(:[a-zA-Z0-9\\.&%$-]+)*@)*"
            + "((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
            + "\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])"
            + "|localhost|([a-zA-Z0-9-]+\\.)*[a-zA-Z0-9-]+\\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\\/($|[a-zA-Z0-9\\.,?\\'\\\\+&%$#=~_-]+))*$";
    /**
     * 统一社会信用代码
     */
    public static final String UNIFIED_SOCIAL_CREDIT_CODE = "^([159Y]{1})([1239]{1})([0-9ABCDEFGHJKLMNPQRTUWXY]{6})([0-9ABCDEFGHJKLMNPQRTUWXY]{9})([0-90-9ABCDEFGHJKLMNPQRTUWXY])$";

    public static final String CHINESE_CHARACTER = "^[\\u4e00-\\u9fa5]{0,}$";

}
