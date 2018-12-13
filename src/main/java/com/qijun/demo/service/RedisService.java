package com.qijun.demo.service;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis服务接口
 */
public interface RedisService {
    /**
     * String类型操作
     * @return
     */
    ValueOperations<String, Object> getValueOperations();

    /**
     * Hash类型操作
     * @return
     */
    HashOperations<String, Object, Object> getHashOperations();

    /**
     * List类型操作
     * @return
     */
    ListOperations<String, Object> getListOperations();

    /**
     * Set类型操作
     * @return
     */
    SetOperations<String, Object> getSetOperations();

    /**
     * ZSet类型操作
     * @return
     */
    ZSetOperations<String, Object> getZSetOperations();



    /****************************** 通用 ***************************************/
    /**
     * 删除缓存
     * @param key
     */
    Boolean delete(final String key);

    /**
     * 删除多个键值对应缓存
     * @param keys
     */
    Long delete(final Collection<String> keys);

    /**
     * 批量删除满足pattern的键值
     * @param pattern
     */
    Long removePattern(final String pattern);

    /**
     * 判断键值是否存在
     * @param key
     * @return
     */
    Boolean hasKey(final String key);

    /**
     * 设置过期时间
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    Boolean expire(final String key, final long timeout, final TimeUnit unit);

    /**
     * 设置过期时间
     * @param key
     * @param date
     * @return
     */
    Boolean expireAt(final String key, final Date date);

    /**
     * 移除键值的过期时间
     * @param key
     * @return
     */
    Boolean persist(final String key);


    /**
     * 获取键值对应的类型
     * @param key
     * @return
     */
    DataType type(final String key);


    /**
     * 在连接中执行指定的动作
     * @param action
     * @param exposeConnection
     * @return
     */
    <T> T execute(RedisCallback<T> action, boolean exposeConnection);

    /**
     * 执行redies脚本
     * @param script
     * @param keys
     * @param args
     * @return
     */
    <T> T execute(RedisScript<T> script, List<String> keys, Object... args);

    /**
     * 获取满足pattern的所有键值
     * @param pattern
     * @return
     */
    Set<String> keys(String pattern);

}
