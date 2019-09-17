package com.platform.auth.dao;

import com.platform.auth.entity.RestOperatorLogEntity;
import com.platform.auth.entity.RestOperatorLogEntityExample;

import tk.mybatis.mapper.common.Mapper;

/**
 * 请求日志实体DAO
 * @version: 1.0
 */
public interface RestOperatorLogEntityDao extends Mapper<RestOperatorLogEntity> {

    int countByExample(RestOperatorLogEntityExample example);
}