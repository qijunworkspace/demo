package com.qijun.demo.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 通用的服务方法
 * @author Qijun
 * @date 12/14/18 1:40 PM
 * @version 1.0
 */
public interface BaseService<T, KEY> {

    /**
     * 获取所有数据
     * @return list
     */
    List<T> findAll();


    /**
     * 获取对象
     * @param id 主键
     * @return 对象
     */
    T get(KEY id);

    /**
     * 插入记录数据
     * @param record 记录
     * @return 插入个数
     */
    int insert(T record);

    /**
     * 删除记录
     * @param id 主键
     * @return 删除个数
     */
    int delete(KEY id);

    /**
     * 更新记录数据
     * @param record 记录
     * @return 更新个数
     */
    int update(T record);

    /**
     * 选择性更新记录
     * @param record 记录
     * @return 更新个数
     */
    int updateSelective(T record);


}
