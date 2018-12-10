# Redis学习总结
> 缓存中应存储*不常改变的数据*，如系统菜单项、用户角色权限、数据字典、配置信息等。
**缓存分级**：常用的数据放在一级缓存(内存)中，不常用的数据放在Redis等二级缓存中。

### 1. Redis常用命令
+ `redis-cli -h host -p port` 连接Redis服务
+ `auth password` 连接命令行后验证密码
+ `set key value` 设置键值对
+ `mget key1 key2` 获取多个值
+ `keys x?x*` 模糊查询符合的键值
+ `exists key` 判断键值是否存在
+ `del key` 删除对应键值信息
+ `expire key time` 设置键值多长秒后过期
+ `ttl key` 查看键值剩余的存活时间

### 2. 五种数据类型
+ 字符串(String)
+ 哈希/散列/字典(Hash)，放Key-value，可在springboot中存放session数据
    1. `hset key eKey eValue` 插入键值对数据
    2. `hget key eKey` 获取值数据
    3. `hexist key eKey` 检测对应键是否存在
    4. `hkeys key` 获取键对应的所有字段键
    5. `hgetall key` 获取键对应的所有键值对
    6. `hdel key ekey1 ekey2` 删除字段键值对
+ 列表(List)，添加速度快，查询较慢
    1. 插入/获取命令 lpush/rpush/lrange/rpop/lpop/llen
    2. 阻塞时长命令 `blpop/brpop key [time]`
+ 集合(Set)
    1. `sadd key member1 [member2]` 插入元素
    2. `srem key member1 [member2]` 删除元素
    3. `smember key` 获取集合中所有的元素
    4. `sinter key1 key2` 返回两个集合的交集
    5. `sinterstore key1 key2 key3` 将key1和key2集合中交集放到key3中
    6. `sunion key1 key2` 返回合并的集合
+ 有序集合(Sorted Set)

### 3. 发布订阅
- `subscribe key` 订阅指定的键值
- `publish key value` 向指定的键发布消息
- `psubscribe key*/key?/key[123]` 订阅符合模式的键消息

### 4.Springboot集成
- RedisTemplate默认使用JDK序列化
- RedisConnection用于低级别(byte数组)的Redis API操作，具体使用JRedis或Lettuce(LettuceConnectionFactory)
- 实现MessageListenerAdapter可注册在RedisMessageListenerContainer实现指定键值消息监听
- 使用redisTemplate.convertAndSend(queueKey, message)发布消息