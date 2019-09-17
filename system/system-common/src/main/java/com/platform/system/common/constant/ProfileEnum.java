package com.platform.system.common.constant;

/**
 * 属性
 * @version: 1.0
 */
public enum ProfileEnum {
    /**
     * 开发环境
     */
    DEV("dev"),
    /**
     * 测试环境
     */
    TEST("test"),
    /**
     * 测试环境
     */
    SIT("sit"),
    /**
     * 验收环境
     */
    UAT("uat"),
    /**
     * 生产环境
     */
    PRODUCT("product");

    private final String profile;

    ProfileEnum(String profile) {
        this.profile = profile;
    }

    public String getProfile(){
        return profile;
    }
}
