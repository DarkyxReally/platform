package com.platform.user.service.impl;

import com.platform.system.common.service.impl.BaseCrudServiceImpl;

import tk.mybatis.mapper.common.Mapper;


/**
 * 抽象用户service类
 * @version: 1.0
 * @param <M>
 * @param <T>
 */
public abstract class AbstractUserService<M extends Mapper<T>, T> extends BaseCrudServiceImpl<Mapper<T>, T> {

    
}
