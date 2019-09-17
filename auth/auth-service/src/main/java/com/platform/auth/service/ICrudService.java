package com.platform.auth.service;

import java.util.List;

/**
 * CRUD接口
 */
public interface ICrudService<T> {

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     * @param entity
     * @return
     */
    T selectOne(T entity);

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     * @param id
     * @return
     */
    T selectById(Object id);

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     * @param entity
     * @return
     */
    List<T> selectList(T entity);

    /**
     * 查询全部结果
     * @return
     */
    List<T> selectListAll();

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     * @param entity
     * @return
     */
    long selectCount(T entity);

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     * @param entity
     */
    int insertSelective(T entity);

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     * @param id
     */
    int deleteById(Object id);

    /**
     * 根据主键更新属性不为null的值
     * @param entity
     */
    int updateSelectiveById(T entity);

    /**
     * 根据Example条件进行修改
     * @param entity
     * @param example
     * @return
     */
    int updatetByExample(T entity, Object example);
    
    /**
     * 根据Example条件进行查询
     * @param example
     * @return
     */
    List<T> selectByExample(Object example);

    /**
     * 根据Example条件进行查询总数
     * @param example
     * @return
     */
    int selectCountByExample(Object example);
    
}
