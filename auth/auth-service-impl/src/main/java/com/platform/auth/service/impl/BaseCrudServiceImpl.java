package com.platform.auth.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.platform.auth.service.ICrudService;

import tk.mybatis.mapper.common.Mapper;


/**
 * 基本CRUD service基类
 * @version: 1.0
 * @param <M>
 * @param <T>
 */
public abstract class BaseCrudServiceImpl<M extends Mapper<T>, T> implements ICrudService<T>{

    protected Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected M baseEntityDao;

    /**
     * 设置实际的Mapper
     * @param baseDao
     */
    protected void setBaseEntityDao(M baseDao){
        this.baseEntityDao = baseDao;
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     * @param entity
     * @return
     */
    @Override
    public T selectOne(T entity){
        return baseEntityDao.selectOne(entity);
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     * @param id
     * @return
     */
    @Override
    public T selectById(Object id){
        return baseEntityDao.selectByPrimaryKey(id);
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     * @param entity
     * @return
     */
    @Override
    public List<T> selectList(T entity){
        return baseEntityDao.select(entity);
    }

    /**
     * 查询全部结果
     * @return
     */
    @Override
    public List<T> selectListAll(){
        return baseEntityDao.selectAll();
    }

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     * @param entity
     * @return
     */
    @Override
    public long selectCount(T entity){
        return new Long(baseEntityDao.selectCount(entity)).longValue();
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     * @param entity
     */
    @Override
    public int insertSelective(T entity){
        return baseEntityDao.insertSelective(entity);
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     * @param id
     */
    @Override
    public int deleteById(Object id){
        return baseEntityDao.deleteByPrimaryKey(id);
    }

    /**
     * 根据主键更新属性不为null的值
     * @param entity
     */
    @Override
    public int updateSelectiveById(T entity){
        return baseEntityDao.updateByPrimaryKeySelective(entity);
    }
    
    @Override
    public int updatetByExample(T entity, Object example){
        return baseEntityDao.updateByExampleSelective(entity, example);
    }

    /**
     * 根据Example条件进行查询
     * @param example
     * @return
     */
    @Override
    public List<T> selectByExample(Object example){
        return baseEntityDao.selectByExample(example);
    }

    /**
     * 根据Example条件进行查询总数
     * @param example
     * @return
     */
    @Override
    public int selectCountByExample(Object example){
        return baseEntityDao.selectCountByExample(example);
    }
}
