package com.platform.system.common.constant;


/**
 * 枚举类标记接口, 该模块下的所有枚举类都实现该接口, 方便查询统计有哪些枚举
 * @version: 1.0
 */
public interface IEnumUserCode {

    /**
     * 枚举值(数字型的枚举值)
     * @return
     */
    int intCode();
    
    /**
     * 枚举值(字符串型的枚举值)
     * @return
     */
    String strCode();

    /**
     * 描述
     * @return
     */
    String message();
    
    /**
     * 根据code获取枚举值
     * @param code
     * @return
     */
    IEnumUserCode valueOfStrCode(String code);
    
    /**
     * 根据code获取枚举值
     * @param code
     * @return
     */
    IEnumUserCode valueOfIntCode(int code);
}
