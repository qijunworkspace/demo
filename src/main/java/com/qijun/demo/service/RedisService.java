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
 * @author Qijun
 * @date ate 11/28/18 11:36 AM
 * @version 1.0
 */
public interface RedisService {
    /**
     * String类型操作
     * @return ValueOperations
     */
     ValueOperations<String, Object> getValueOperations();

    /**
     * Hash类型操作
     * @return HashOperations
     */
     HashOperations<String, Object, Object> getHashOperations();

    /**
     * List类型操作
     * @return ListOperations
     */
     ListOperations<String, Object> getListOperations();

    /**
     * Set类型操作
     * @return SetOperations
     */
     SetOperations<String, Object> getSetOperations();

    /**
     * ZSet类型操作
     * @return ZSetOperations
     */
     ZSetOperations<String, Object> getZSetOperations();


    /**
     * 删除缓存
     * @param key 键值
     * @return 是否删除成功
     */
     Boolean delete(final String key);

    /**
     * 删除多个键值对应缓存
     * @param keys 主键集合
     * @return 删除个数
     */
     Long delete(final Collection<String> keys);

    /**
     * 批量删除满足pattern的键值
     * @param pattern 键值模式
     * @return 删除个数
     */
     Long removePattern(final String pattern);

    /**
     * 判断键值是否存在
     * @param key 键值
     * @return 是否存在
     */
     Boolean hasKey(final String key);

    /**
     * 设置过期时间
     * @param key 主键
     * @param timeout 时长
     * @param unit 时长单位
     * @return 是否设置成功
     */
     Boolean expire(final String key, final long timeout, final TimeUnit unit);

    /**
     * 设置过期时间
     * @param key 主键
     * @param date 过期时间
     * @return 是否设置成功
     */
     Boolean expireAt(final String key, final Date date);

    /**
     * 移除键值的过期时间
     * @param key 主键
     * @return 是否持久化成功
     */
     Boolean persist(final String key);


    /**
     * 获取键值对应的类型
     * @param key 主键
     * @return 数据类型
     */
     DataType type(final String key);


    /**
     * 在连接中执行指定的动作
     * @param action 回调操作
     * @param exposeConnection whether to enforce exposure of the native Redis Connection to callback code
     * @return 回调结果对象
     */
     <T> T execute(RedisCallback<T> action, boolean exposeConnection);

    /**
     * 执行redis脚本
     * @param script 脚本
     * @param keys Any keys that need to be passed to the script
     * @param args Any args that need to be passed to the script
     * @return 模板对象
     */
     <T> T execute(RedisScript<T> script, List<String> keys, Object... args);

    /**
     * 获取满足pattern的所有键值
     * @param pattern 匹配模式
     * @return 匹配的键值集合
     */
     Set<String> keys(String pattern);

}
