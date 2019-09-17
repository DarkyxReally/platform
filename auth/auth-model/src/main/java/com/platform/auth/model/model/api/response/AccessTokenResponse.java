package com.platform.auth.model.model.api.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * accessToken 响应
 * @version: 1.0
 */
@Getter
@Setter
public class AccessTokenResponse {

    /**
     * token
     */
    private String token;

    /**
     * 过期时间
     */
    private Date expire;
}
