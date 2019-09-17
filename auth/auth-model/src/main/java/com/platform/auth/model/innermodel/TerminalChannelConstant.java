package com.platform.auth.model.innermodel;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.platform.auth.model.model.response.constant.IEnumCode;

/**
 * 终端渠道
 * @version: 1.0
 */
public class TerminalChannelConstant {

    /**
     * APP
     */
    public static final String CHANNEL_APP = "1";
    /**
     * 微信小程序
     */
    public static final String CHANNEL_WECHAT_MINI = "2";

    /**
     * WEB
     */
    public static final String CHANNEL_WEB = "3";
    
    /**
     * 终端渠道
     */
    public enum TerminalChannel implements IEnumCode{

        /** APP 端登录 */
        APP(TerminalChannelConstant.CHANNEL_APP, "APP"),
        
        /** 微信小程序  端登录*/
        WECHAT_MINI(TerminalChannelConstant.CHANNEL_WECHAT_MINI, "微信小程序"),

        /** WEB 端登录*/
        WEB(TerminalChannelConstant.CHANNEL_WEB, "WEB端登录");

        private final String code;

        private final String message;

        private TerminalChannel(String code, String message) {
            this.code = code;
            this.message = message;
        }

        private static final ImmutableMap<String, TerminalChannel> CACHE;

        static{
            List<String> codeList = new ArrayList<String>();
            for(TerminalChannel statusCode : values()){
                // 防止code重复引起歧义
                if(codeList.contains(statusCode.strCode())){ throw new IllegalStateException("code重复>>" + statusCode); }
                codeList.add(statusCode.strCode());
            }

            final ImmutableMap.Builder<String, TerminalChannel> builder = ImmutableMap.builder();
            for(TerminalChannel statusCode : values()){
                builder.put(statusCode.strCode(), statusCode);
            }
            CACHE = builder.build();
        }

        /**
         * 根据code获取枚举
         * @param code
         * @return
         */
        public static TerminalChannel valueOfCode(String code){
            final TerminalChannel status = CACHE.get(code);
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
        public TerminalChannel valueOfStrCode(String code){
            return valueOfCode(code);
        }

        @Override
        public TerminalChannel valueOfIntCode(int code){
            throw new IllegalArgumentException("该枚举类不支持数字型code");
        }
    } 
}
