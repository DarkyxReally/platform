package com.platform.auth.model.model.api.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信登陆
 * @version: 1.0
 */
@Getter
@Setter
public class WechatLoginResponse extends OpenLoginResponse {

    private static final long serialVersionUID = 6298675245127276707L;
    
    /**
     * 第三方头像
     */
    private String head_img_3rd;
    /**
     * 第三方昵称
     */
    private String nick_name_3rd;
}
