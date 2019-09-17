package com.platform.system.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 正则表达式工具类
 * @version: 1.0
 */
public class RegexUtil {

    /**
     * IP地址
     */
    private static final String IP_ADDRESS = "^([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

    /**
     * 邮箱
     */
    private static final String EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    /**
     * 身份证
     */
    private static final String IDENTIFY = "^\\d{15}(\\d{2}[0-9xX])?$";

    /**
     * 护照
     */
    private static final String HZ = "^[a-zA-Z](\\d{8}|\\d{7})$";
    
    /**
     * 简要方式:手机号表达式
     */
    private static final String SIMPLE_MOBILE_PATTERN = "^1\\d{10}$";

    /**
     * 手机号数组
     */
    private static final String[] MOBILE_ARR = new String[]{
        // 移动
        "134", "135", "136", "137", "138", "139",
        "147", "148", 
        "150", "151", "152", "157", "158", "159",
        "177", "178", "179",
        "182", "183", "184", "187", "188",  
        "198", 
        
        // 联通
        "130", "131", "132", 
        "145", "146",
        "155", "156", 
        "166", "167", "168", "169",
        "175", "176",
        "185", "186", 
        "197", "198", "199",
        
        // 电信
        "133", 
        "149", 
        "153", 
        "173", "174", "177", 
        "180", "181", "189", 
        "199"
    };

    /**
     * 手机号数组(含运营小号)
     */
    private static final String[] MOBILE_ARR_WITH_MAINTAIN = new String[]{
        // 移动
        "134", "135", "136", "137", "138", "139",
        "147", "148", 
        "150", "151", "152", "157", "158", "159",
        "177", "178", "179",
        "182", "183", "184", "187", "188",  
        "198", 
        
        // 联通
        "130", "131", "132", 
        "145", "146",
        "155", "156", 
        "166", "167", "168", "169",
        "175", "176",
        "185", "186", 
        "197", "198", "199",
        
        // 电信
        "133", 
        "149", 
        "153", 
        "173", "174", "177",
        "180", "181", "189", 
        "199",

        // 运营小号
        // 移动
        "239", "238", "237", "236", "235", "234", "247", "250", "251", "252", "257", "258", "259",
        // 联通
        "230", "231", "232", "255", "256", "285", "286",
        // 电信
        "233", "253", "280", "281", "289"};
    
    /**
     * 虚拟运营商号码段
     */
    private static final String[] VIRSUAL_MOBILE_OPERATOR = new String[]{
        "170", "171", "172"
    };

    /**
     * 手机号表达式
     */
    public static final String MOBILE_PATTERN = "^(" + StringUtils.join(MOBILE_ARR, "|") + ")\\d{8}$";
    
    /**
     * 虚拟运营商手机号表达式
     */
    public static final String VIRSUAL_MOBILE_PATTERN = "^(" + StringUtils.join(VIRSUAL_MOBILE_OPERATOR, "|") + ")\\d{8}$";

    /**
     * 手机号表达式(含运营小号)
     */
    public static final String MOBILE_PATTERN_WITH_MAINTAIN = "^(" + StringUtils.join(MOBILE_ARR_WITH_MAINTAIN, "|") + ")\\d{8}$";
    
    /**
     * 验证特殊符号
     */
    public static final String SPECIAL_SYMBOL = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    
    /**
     * 判断是否为ip地址
     * @param ip
     * @return
     */
    public static boolean isIPAddress(String ip){
        return Pattern.compile(IP_ADDRESS).matcher(ip).matches();
    }

    /**
     * 判断是否为手机号
     * @param phone
     * @return
     */
    public static boolean isMobile(String phone){
        return Pattern.compile(MOBILE_PATTERN).matcher(phone).matches();
    }
    
    /**
     * 简要比对是否为手机号
     * @version: 1.0
     * @param phone
     * @return
     */
    public static boolean isMobileBySimpleCheck(String phone){
        return Pattern.compile(SIMPLE_MOBILE_PATTERN).matcher(phone).matches();
    }
    
    /**
     * 判断是否为虚拟运营商手机号
     * @param phone
     * @return
     */
    public static boolean isVirsualMobile(String phone){
        return Pattern.compile(VIRSUAL_MOBILE_PATTERN).matcher(phone).matches();
    }

    /**
     * 此校验放行系统后台创建的小号
     * @param phone
     * @return
     */
    public static boolean isSystemMobile(String phone){
        return Pattern.compile(MOBILE_PATTERN_WITH_MAINTAIN).matcher(phone).matches();
    }

    /**
     * 邮箱地址
     * @param line
     * @return
     */
    public static boolean isEmail(String line){
        Matcher m = Pattern.compile(EMAIL).matcher(line);
        return m.find();
    }

    /**
     * 判断是否符合身份证的 字符串格式
     * 身份证 15或18位 仅数字+字母
     * @param str
     * @return
     */
    public static boolean isSFZ(String str){
        String s = str.trim();
        Matcher match = Pattern.compile(IDENTIFY).matcher(s);
        return match.matches();
    }

    /**
     * 判断是否符合护照的 字符串格式
     * @param str
     * @return
     */
    public static boolean isHZ(String str){
        String s = str.trim();
        Matcher match = Pattern.compile(HZ).matcher(s);
        return match.matches();
    }
    
    /**
     * 字符串是否数字
     * @version: 1.0
     * @param str
     * @return
     */
    public static final boolean isNumeric(String str){
        Matcher isNum = Pattern.compile("^[0-9]*$").matcher(str);
        if(isNum.matches()){
            return true; 
        } 
        return false;
    }
    /**
     * 判断是否存在特殊符号
     * @param str
     * @return true=存在，false=不存在
     */
    public static final boolean isSpecialSymbol(String str){
        Pattern p = Pattern.compile(SPECIAL_SYMBOL); 
        Matcher m = p.matcher(str);
        return m.find();
    }
    
    /**
     * 通过指定的表达式判断是否匹配特定的数据
     * @version: 1.0
     * @param pattern
     * @param value
     * @return
     */
    public static final boolean isMatch(String pattern, String value){
        Pattern p = Pattern.compile(pattern); 
        Matcher m = p.matcher(value);
        return m.find();
    }
}
