package com.platform.auth.model.innermodel.token;

import java.util.Date;

/***
 * token接口
 * @version: 1.0
 */
public interface IToken {

    /**
     * token字符串
     * @return
     */
    String token();
    
    /**
     * 过期时间
     * @return
     */
    Date expired();
    
}
