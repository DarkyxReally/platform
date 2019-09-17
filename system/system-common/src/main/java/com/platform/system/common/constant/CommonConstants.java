package com.platform.system.common.constant;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;


/**
 * 公共常量,在不同模块之间共用
 * @version: 1.0
 */
public class CommonConstants {
    
    /**
     * UTF-8编码字符串
     */
    public static final String UTF8 = "UTF-8";
    
    /**
     * UTF-8编码字符集
     */
    public static final Charset UTF_8 = Charset.forName(UTF8);

    /**
     * 逗号
     */
    public static final String COMMA = ",";
    
    /**
     * 冒号
     */
    public static final String COLON = ":";
    
    /**
     * 当前版本号
     */
    public static final String HEADER_VERSION= "verCode";
    
    /**
     * APP平台：1android，2ios
     */
    public static final String HEADER_PLATFORM = "platform";
    
    /**
     * 手机型号
     */
    public static final String HEADER_MOBILEMODEL= "mobileModel";
    
    /**
     * 设备唯一标识
     */
    public static final String HEADER_DEVICE= "idDevice";
    
    
    /**
     * 安卓平台
     */
    public static final String PLATFORM_ANDROID = "1";
    /**
     * IOS平台
     */
    public static final String PLATFORM_IOS = "2";
    
    /**
     * 微信小程序平台
     */
    public static final String PLATFORM_WECHAT_MINI = "3";
    
    /**
     * 终端平台
     * @version: 1.0
     */
    public enum TerminalPlatform implements IEnumCode{

        /** 安卓 */
        ANDROID(CommonConstants.PLATFORM_ANDROID, "安卓"),
        
        /** IOS */
        IOS(CommonConstants.PLATFORM_IOS, "IOS"),

        /** 微信小程序 */
        WECHAT_MINI(CommonConstants.PLATFORM_WECHAT_MINI, "微信小程序");

        private final String code;

        private final String message;

        private TerminalPlatform(String code, String message) {
            this.code = code;
            this.message = message;
        }

        private static final ImmutableMap<String, TerminalPlatform> CACHE;

        static{
            List<String> codeList = new ArrayList<String>();
            for(TerminalPlatform statusCode : values()){
                // 防止code重复引起歧义
                if(codeList.contains(statusCode.strCode())){ throw new IllegalStateException("code重复>>" + statusCode); }
                codeList.add(statusCode.strCode());
            }

            final ImmutableMap.Builder<String, TerminalPlatform> builder = ImmutableMap.builder();
            for(TerminalPlatform statusCode : values()){
                builder.put(statusCode.strCode(), statusCode);
            }
            CACHE = builder.build();
        }

        /**
         * 根据code获取枚举
         * @return
         */
        public static TerminalPlatform valueOfCode(String code){
            final TerminalPlatform status = CACHE.get(code);
            if(status == null){ throw new IllegalArgumentException("No matching constant for [" + code + "]"); }
            return status;
        }

        @Override
        public String message(){
            return message;
        }

        @Override
        public int intCode(){
            throw new IllegalArgumentException("该枚举类不支持数字型code");
        }

        @Override
        public String strCode(){
            return code;
        }

        @Override
        public TerminalPlatform valueOfStrCode(String code){
            return valueOfCode(code);
        }

        @Override
        public TerminalPlatform valueOfIntCode(int code){
            throw new IllegalArgumentException("该枚举类不支持数字型code");
        }
    } 
    
}
