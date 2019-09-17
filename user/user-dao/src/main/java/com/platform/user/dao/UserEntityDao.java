package com.platform.user.dao;

import org.apache.ibatis.annotations.Param;

import com.platform.user.entity.UserEntity;
import com.platform.user.entity.UserEntityExample;
import com.platform.user.innermodel.dto.UserInfoDTO;

import tk.mybatis.mapper.common.Mapper;

public interface UserEntityDao extends Mapper<UserEntity> {
    int countByExample(UserEntityExample example);
    
    UserInfoDTO selectByUnionId(@Param("unionId") String unionId);
}