package com.platform.auth.model.model.response.constant;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;

/**
 * 用户类型常量
 * @version: 1.0
 */
public interface UserTypeConstant {
    
    /**
     * APP用户类型
     */
    public static final String TYPE_APP_USER = "1";
    
    /**
     * 管理后台用户
     */
    public static final String TYPE_AMDIN_USER = "2";
    
    /**
     * 商户后台用户
     */
    public static final String TYPE_MERCHANT_ADMIN_USER = "3";

    /**
     * 企业用户
     */
    public static final String TYPE_ENTERPRISE = "4";
    
    /**
     * 商户收银员
     */
    public static final String TYPE_MERCHANT_CASHIER = "5";
    
    /**
     * 品牌后台用户
     */
    public static final String TYPE_BRAND_ADMIN_USER = "6";
    
    /**
     * 登陆用户类别枚举
     */
    public enum UserType implements IEnumCode {

        /** APP用户 */
        APP_USER(UserTypeConstant.TYPE_APP_USER, "APP"),
        /** 管理后台用户 */
        AMDIN_USER(UserTypeConstant.TYPE_AMDIN_USER, "管理后台用户"),
        /** 商户后台用户 */
        MERCHANT_ADMIN_USER(UserTypeConstant.TYPE_MERCHANT_ADMIN_USER, "商户后台用户"),
        /** 企业用户  */
        ENTERPRISE_USER(UserTypeConstant.TYPE_ENTERPRISE, "企业用户"),
        /** 商户收银员 */
        MERCHANT_CASHIER(UserTypeConstant.TYPE_MERCHANT_CASHIER, "商户收银员"),
        /** 品牌后台用户 */
        BRAND_ADMIN_USER(UserTypeConstant.TYPE_BRAND_ADMIN_USER, "品牌后台用户")
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
