package com.qijun.demo.service.impl;

import com.qijun.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis服务类实现方法
 * @author Qijun
 * @date 12/13/18 3:06 PM
 * @version 1.0
 */
@Service
public class RedisServiceImpl implements RedisService {

    /**
     * 数据操作类
     */
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 键值对
     */
    private ValueOperations<String, Object> valueOperations;
    /**
     * Hash数据
     */
    private HashOperations<String, Object, Object> hashOperations;
    /**
     * List数据
     */
    private ListOperations<String, Object> listOperations;
    /**
     * 集合数据
     */
    private SetOperations<String, Object> setOperations;
    /**
     * 有序集合
     */
    private ZSetOperations<String, Object> zSetOperations;

    /**
     * 初始化操作
     * @param redisTemplate redis模板类
     */
    @Autowired
    public void init(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
        valueOperations = redisTemplate.opsForValue();
        hashOperations = redisTemplate.opsForHash();
        listOperations = redisTemplate.opsForList();
        setOperations = redisTemplate.opsForSet();
        zSetOperations = redisTemplate.opsForZSet();
    }

    /**
     * String类型操作
     * @return ValueOperations
     */
    @Override
    public ValueOperations<String, Object> getValueOperations() {
        return valueOperations;
    }

    /**
     * Hash类型操作
     * @return HashOperations
     */
    @Override
    public HashOperations<String, Object, Object> getHashOperations() {
        return hashOperations;
    }

    /**
     * List类型操作
     * @return ListOperations
     */
    @Override
    public ListOperations<String, Object> getListOperations() {
        return listOperations;
    }

    /**
     * Set类型操作
     * @return SetOperations
     */
    @Override
    public SetOperations<String, Object> getSetOperations() {
        return setOperations;
    }

    /**
     * ZSet类型操作
     * @return ZSetOperations
     */
    @Override
    public ZSetOperations<String, Object> getZSetOperations() {
        return zSetOperations;
    }

    /**
     * 删除缓存
     * @param key 键值
     * @return 是否删除成功
     */
    @Override
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除多个键值对应缓存
     * @param keys 多个键值
     * @return 是否删除成功
     */
    @Override
    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 批量删除满足pattern的键值
     * @param pattern 键值模式
     * @return 删除个数
     */
    @Override
    public Long removePattern(String pattern) {
        Set<String> keys = this.keys(pattern);
        if (keys.size() > 0) {
            return this.delete(keys);
        }
        return 0L;
    }

    /**
     * 判断键值是否存在
     * @param key 键值
     * @return 是否存在
     */
    @Override
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间
     * @param key 主键
     * @param timeout 时长
     * @param unit 时长单位
     * @return 是否设置成功
     */
    @Override
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 设置过期时间
     * @param key 主键
     * @param date 过期时间
     * @return 是否设置成功
     */
    @Override
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 移除键值的过期时间
     * @param key 主键
     * @return 是否持久化成功
     */
    @Override
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 获取键值对应的类型
     * @param key 主键
     * @return 数据类型
     */
    @Override
    public DataType type(String key) {
        return redisTemplate.type(key);
    }

    /**
     * 在连接中执行指定的动作
     * @param action 回调操作
     * @param exposeConnection whether to enforce exposure of the native Redis Connection to callback code
     * @return 回调结果对象
     */
    @Override
    public <T> T execute(RedisCallback<T> action, boolean exposeConnection) {
        return redisTemplate.execute(action, exposeConnection);
    }

    /**
     * 执行redis脚本
     * @param script 脚本
     * @param keys Any keys that need to be passed to the script
     * @param args Any args that need to be passed to the script
     * @return 模板对象
     */
    @Override
    public <T> T execute(RedisScript<T> script, List<String> keys, Object... args) {
        return redisTemplate.execute(script, keys, args);
    }

    /**
     * 获取满足pattern的所有键值
     * @param pattern 匹配模式
     * @return 匹配的键值集合
     */
    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
}
