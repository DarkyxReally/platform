package com.platform.system.common.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platform.system.common.service.CrudService;
import com.platform.system.common.util.Query;

/**
 * 基本CRUD service基类
 * @version: 1.0
 * @param <M>
 * @param <T>
 */
public abstract class BaseCrudServiceImpl<M extends Mapper<T>, T> implements CrudService<T>{

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
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     * @param entity
     */
    @Override
    public int insert(T entity){
        return baseEntityDao.insert(entity);
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
     * 根据实体属性作为条件进行删除，查询条件使用等号
     * @param entity
     */
    @Override
    public int delete(T entity){
        return baseEntityDao.delete(entity);
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
     * 根据主键更新实体全部字段，null值会被更新
     * @param entity
     */
    @Override
    public int updateById(T entity){
        return baseEntityDao.updateByPrimaryKey(entity);
    }

    /**
     * 根据主键更新属性不为null的值
     * @param entity
     */
    @Override
    public int updateSelectiveById(T entity){
        return baseEntityDao.updateByPrimaryKeySelective(entity);
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

    /**
     * 根据条件查询(注意:所有字段都为模糊查询)
     * 默认按照每页10条数据进行分页
     * @param query
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public Page<T> selectByQuery(Query query){
        Class<T> clazz = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        for(Map.Entry<String, Object> entry : query.entrySet()){
            criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
        }
        Page<T> result = PageHelper.startPage(query.getPage(), query.getLimit());
        baseEntityDao.selectByExample(example);
        return result;
    }

    /**
     * 根据Example条件更新属性不为null的值
     * @param example
     * @param entity
     * @return
     */
    @Override
    public int updateByExampleSelective(T entity, Object example) {
        return baseEntityDao.updateByExampleSelective(entity, example);
    }
}
