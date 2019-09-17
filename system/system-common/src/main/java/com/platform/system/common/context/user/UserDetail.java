package com.platform.system.common.context.user;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户详情基类
 * @version: 1.0
 */
@Getter
@Setter
public abstract class UserDetail implements Serializable, IUserDetail {

    private static final long serialVersionUID = 4833465884940650145L;
    
    /**
     * 用户id
     */
    private String idUser;
    
    /**
     * 昵称
     */
    private String nickName;

    @Override
    public String nickName(){
        return this.nickName;
    }

    @Override
    public String userId(){
        return this.idUser;
    }
}
