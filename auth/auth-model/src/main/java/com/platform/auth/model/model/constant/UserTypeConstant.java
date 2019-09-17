package com.platform.auth.model.model.constant;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.platform.system.common.constant.IEnumCode;

/**
 * 用户类型常量
 * @version: 1.0
 */
public interface UserTypeConstant {
    
    /**
     * 小程序用户类型
     */
    public static final String TYPE_WECHATMINI_USER = "1";
    
    /**
     * 管理后台用户
     */
    public static final String TYPE_COMMON_USER = "2";
    
    
    /**
     * 登陆用户类别枚举
     */
    public enum UserType implements IEnumCode {

        /** APP用户 */
    	WECHATMINI_USER(UserTypeConstant.TYPE_WECHATMINI_USER, "小程序用户"),
        /** 管理后台用户 */
        AMDIN_USER(UserTypeConstant.TYPE_COMMON_USER, "普通用户"),
        ;

        private final String code;

        private final String message;

        private UserType(String code, String message) {
            this.code = code;
            this.message = message;
        }

        private static final ImmutableMap<String, UserType> CACHE;

        static{
            List<String> codeList = new ArrayList<String>();
            for(UserType statusCode : values()){
                // 防止code重复引起歧义
                if(codeList.contains(statusCode.strCode())){ throw new IllegalStateException("code重复>>" + statusCode); }
                codeList.add(statusCode.strCode());
            }

            final ImmutableMap.Builder<String, UserType> builder = ImmutableMap.builder();
            for(UserType statusCode : values()){
                builder.put(statusCode.strCode(), statusCode);
            }
            CACHE = builder.build();
        }

        /**
         * 根据code获取枚举
         * @param code
         * @return
         */
        public static UserType valueOfCode(String code){
            final UserType status = CACHE.get(code);
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
        public UserType valueOfStrCode(String code){
            return valueOfCode(code);
        }

        @Override
        public UserType valueOfIntCode(int code){
            throw new IllegalArgumentException("该枚举类不支持数字型code");
        }
    }
}
