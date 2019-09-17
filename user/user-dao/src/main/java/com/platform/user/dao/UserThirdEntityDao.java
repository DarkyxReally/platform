package com.platform.user.dao;

import com.platform.user.entity.UserThirdEntity;
import com.platform.user.entity.UserThirdEntityExample;
import tk.mybatis.mapper.common.Mapper;

public interface UserThirdEntityDao extends Mapper<UserThirdEntity> {
    int countByExample(UserThirdEntityExample example);
}