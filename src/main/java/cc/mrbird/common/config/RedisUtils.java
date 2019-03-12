package cc.mrbird.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述: redis工具类
 *
 * @auther: 钮豪
 * @date: 2018/11/1 14:20
 */
@SuppressWarnings("unchecked")
@Component
public class RedisUtils {

    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Object> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存，并设置失效时间
     * redisUtils.set("niuhao11", "niuhao11", 10, TimeUnit.SECONDS);//TimeUnit.MINUTES
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, int expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */

    public double hincr(String key, String item, double by) {
        Double increment = redisTemplate.opsForHash().increment(key, item, by);
        return increment;
    }


    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */

    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                redisTemplate.expire(key, expireTime, TimeUnit.MINUTES);
                //expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */

    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */

    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }


    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */

    public Map<Object, Object> hmget(String key) {

        return redisTemplate.opsForHash().entries(key);

    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 正序获取sortedSet对应的所有键值
     *获取队列的方法，start表示起始位置的index，从0开始。index表示end的位置，-1表示获取全部
     * @param key 键
     * @return 对应的多个键值 ,返回的时候回带上score
     */

    public Set<ZSetOperations.TypedTuple<Object>> getZSetRange(String key, long start, long end) {

        return redisTemplate.opsForZSet().rangeWithScores(key,start,end);

    }

    /**
     *倒叙
     * @param key
     * @param start
     * @param end
     * @return返回的时候回带上score
     */
    public Set<ZSetOperations.TypedTuple<Object>>getZSetReverseRange(String key, long start, long end) {

        return redisTemplate.opsForZSet().reverseRangeWithScores(key,start,end);

    }
    /**
     * 过期时间，默认30分钟
     */
    private Long expireTime = 1000 * 60 * 30L;

    private RedisSerializer<Object> getKeySerializer() {
        return (RedisSerializer<Object>) redisTemplate.getKeySerializer();
    }

    private RedisSerializer<Object> getValueSerializer() {
        return (RedisSerializer<Object>) redisTemplate.getValueSerializer();
    }

    private RedisSerializer<Object> getHashKeySerializer() {
        return (RedisSerializer<Object>) redisTemplate.getHashKeySerializer();
    }

    private RedisSerializer<Object> getHashValueSerializer() {
        return (RedisSerializer<Object>) redisTemplate.getHashValueSerializer();
    }

    private byte[] getKeyBytes(Object key) {
        return getKeySerializer().serialize(key);
    }

    private byte[] getHashKeyBytes(Object key) {
        return getHashKeySerializer().serialize(key);
    }

    private byte[] getValueBytes(Object value) {
        return getValueSerializer().serialize(value);
    }

    private byte[] getHashValueBytes(Object value) {
        return getHashValueSerializer().serialize(value);
    }

    private <T> T deserializeValue(byte[] bytes) {
        return (T) getValueSerializer().deserialize(bytes);
    }

    private <T> T deserializeHashValue(byte[] bytes) {
        return (T) getHashValueSerializer().deserialize(bytes);
    }

    /**
     * 添加缓存数据（给定key已存在，进行覆盖）
     *
     * @param key
     * @param value
     * @throws DataAccessException
     */
    public void set(final Object key, final Object value) throws DataAccessException {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.set(getKeyBytes(key), getValueBytes(value));
                return null;
            }
        });
    }

    /**
     * 添加缓存数据（给定key已存在，不进行覆盖，直接返回false）
     * 将 key 的值设为 value ，当且仅当 key 不存在。
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。
     * 设置成功，返回 1
     * 设置失败，返回 0
     *
     * @param key
     * @param value
     * @return 操作成功返回true，否则返回false
     * @throws DataAccessException
     */
    public boolean setNX(final Object key, final Object value) throws DataAccessException {
        boolean result = (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setNX(getKeyBytes(key), getValueBytes(value));
            }
        });
        return result;
    }

    /**
     * 添加缓存数据，设定缓存失效时间
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     * 设置成功时返回 OK 。
     * 当 seconds 参数不合法时，返回一个错误。 @param key
     *
     * @param value
     * @param expireSeconds 过期时间，单位 秒
     * @throws DataAccessException
     */
    public void setEx(final Object key, final Object value, final long expireSeconds) throws DataAccessException {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(getKeyBytes(key), expireSeconds, getValueBytes(value));
                return true;
            }
        });
    }

    /**
     * 添加缓存数据，设定默认缓存失效时间
     *
     * @param key
     * @param value
     * @throws DataAccessException
     */
    public void setEx(Object key, Object value) throws DataAccessException {
        setEx(key, value, expireTime);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
     * 当 key 没有旧值时，也即， key 不存在时，返回 nil
     *
     * @param key
     * @param value
     * @throws DataAccessException
     */
    public void getSet(final Object key, final Object value) throws DataAccessException {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.getSet(getKeyBytes(key), getValueBytes(value));
                return true;
            }
        });
    }


    /**
     * 获取key对应value
     *
     * @param key
     * @return
     * @throws DataAccessException
     */
    public <T> T get(final Object key) throws DataAccessException {
        byte[] result = (byte[]) redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(getKeyBytes(key));
            }
        });
        if (result == null) {
            return null;
        }
        return deserializeValue(result);
    }

    /**
     * 散列表的形式存储
     * 即，key代表表名，fueld代表列名
     * 超时时间只能设置在 大 key 上，单个 filed 则不可以设置超时，再调用此方法后调用expire方法即可
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public boolean hSet(final Object key, final Object field, final Object value) {
        boolean result = (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hSet(getKeyBytes(key), getHashKeyBytes(field), getHashValueBytes(value));
            }
        });
        return result;
    }

    /**
     * 将名称为key的hash中field的value增加integer
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Double hIncrBy(final Object key, final Object field, final double value) {
        Double result = (Double) redisTemplate.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hIncrBy(getKeyBytes(key), getHashKeyBytes(field), value);
            }
        });
        return result;
    }

    /**
     * @param key
     * @param field
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    public <T> T hGet(final Object key, final Object field) throws DataAccessException {
        byte[] result = (byte[]) redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hGet(getKeyBytes(key), getHashKeyBytes(field));
            }
        });
        if (result == null) {
            return null;
        }
        return deserializeHashValue(result);
    }

    /**
     * 设置超时时间
     * 用户创建key之后执行
     *
     * @param key
     * @param seconds
     * @return
     */
    public boolean expire(final Object key, final long seconds) {
        boolean result = (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.expire(getKeyBytes(key), seconds);
            }
        });

        return result;
    }

    /**
     * 删除指定key数据
     *
     * @param key
     * @return 返回操作影响记录数
     */
    public long del(final Object key) {
        if (key == null) {
            return 0l;
        }
        Long delNum = (long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.del(getKeyBytes(key));
            }
        });
        return delNum;
    }

    /**
     * 用于删除哈希表 key 中的一个或多个指定字段，不存在的字段将被忽略
     *
     * @param key   哈希表对应的键
     * @param filed 哈希表中要删除的键
     * @return
     */
    public Long hdel(final Object key, final Object... filed) {
        if (key == null) {
            return 0l;
        }
        final byte[] keys = getKeyBytes(key);
        if (keys == null || keys.length == 0) {
            return 0L;
        }
        Long delNum = (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                List<byte[]> fieldList = new ArrayList<byte[]>();
                for (Object f : filed) {
                    if (f != null) {
                        byte[] hfs = getHashKeyBytes(f);
                        if (hfs != null && hfs.length > 0) {
                            fieldList.add(hfs);
                        }
                    }
                }

                if (fieldList.size() == 0) {
                    return 0L;
                }

                byte[][] bytes = new byte[0][];
                return connection.hDel(keys, fieldList.toArray(bytes));
            }
        });
        return delNum;
    }

    /**
     * 用于查找所有符合给定模式 pattern 的 key
     *
     * @param key
     * @return
     */
    public Set<byte[]> keys(final Object key) {
        if (key == null) {
            return null;
        }
        Set<byte[]> bytesSet = (Set<byte[]>) redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.keys(getKeyBytes(key));
            }
        });

        return bytesSet;
    }

    /**
     * 清空
     */
    public void flushDb() {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.flushDb();
                return null;
            }
        });
    }

    /**
     * size
     */
    public Long dbSize() {
        Long dbSize = (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.dbSize();
            }
        });
        return dbSize;
    }

    public Map<Object, Object> hashGetAll(final String key) throws DataAccessException {
        return redisTemplate.opsForHash().entries(key);
    }
}
