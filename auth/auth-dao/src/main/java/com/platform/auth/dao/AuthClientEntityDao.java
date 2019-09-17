package com.platform.auth.dao;


import com.platform.auth.entity.AuthClientEntity;
import com.platform.auth.entity.AuthClientEntityExample;

import tk.mybatis.mapper.common.Mapper;

public interface AuthClientEntityDao extends Mapper<AuthClientEntity> {
    int countByExample(AuthClientEntityExample example);
    
    AuthClientEntity selectByClientCodeAndClientSecret(AuthClientEntity entity);
}