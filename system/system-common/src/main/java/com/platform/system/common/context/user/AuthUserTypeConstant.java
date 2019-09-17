package com.platform.system.common.context.user;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.platform.system.common.constant.IEnumUserCode;

/**
 * 用户账号类型常量
 * @version: 1.0
 */
public interface AuthUserTypeConstant {
    
    /**
     * APP用户账号
     */
    String TYPE_APP_USER = "1";
    
    /**
     * 运营管理后台账号
     */
    String TYPE_AMDIN_USER = "2";
    
    /**
     * 普通商户后台账号
     */
    String TYPE_MERCHANT_ADMIN_USER = "3";

    /**
     * 企业用户账号:作废
     */
    @Deprecated
    String TYPE_ENTERPRISE = "4";
    
    /**
     * 商户收银员账号
     */
    String TYPE_MERCHANT_CASHIER = "5";
    
    /**
     * O2O品牌账号
     */
    String TYPE_BRAND_ADMIN_USER = "6";
    
    /**
     * O2O商户账号
     */
    String TYPE_O2O_MERCHANT_ADMIN = "7"; 
    
    /**
     * 认证用户类别枚举
     * @version: 1.0
     */
    public enum AuthUserType implements IEnumUserCode {

        /** APP用户 */
        APP_USER(AuthUserTypeConstant.TYPE_APP_USER, "APP"),
        /** 管理后台用户 */
        AMDIN_USER(AuthUserTypeConstant.TYPE_AMDIN_USER, "管理后台用户")
        ;

        private final String code;

        private final String message;

        private AuthUserType(String code, String message) {
            this.code = code;
            this.message = message;
        }

        private static final ImmutableMap<String, AuthUserType> CACHE;

        static{
            List<String> codeList = new ArrayList<String>();
            for(AuthUserType statusCode : values()){
                // 防止code重复引起歧义
                if(codeList.contains(statusCode.strCode())){ throw new IllegalStateException("code重复>>" + statusCode); }
                codeList.add(statusCode.strCode());
            }

            final ImmutableMap.Builder<String, AuthUserType> builder = ImmutableMap.builder();
            for(AuthUserType statusCode : values()){
                builder.put(statusCode.strCode(), statusCode);
            }
            CACHE = builder.build();
        }

        /**
         * 根据code获取枚举
         * @param code
         * @return
         */
        public static AuthUserType valueOfCode(String code){
            final AuthUserType status = CACHE.get(code);
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
        public AuthUserType valueOfStrCode(String code){
            return valueOfCode(code);
        }

        @Override
        public AuthUserType valueOfIntCode(int code){
            throw new IllegalArgumentException("该枚举类不支持数字型code");
        }
    }
}
