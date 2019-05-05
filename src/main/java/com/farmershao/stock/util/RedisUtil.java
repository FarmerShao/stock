
package com.farmershao.stock.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author : Shao Yu
 * @date 2019/5/5 19:19
 * @since : 1.0.0
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, Object> valueOperations;
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Object> hashOperations;
    @Resource(name = "redisTemplate")
    private ListOperations<String, Object> listOperations;
    @Resource(name = "redisTemplate")
    private SetOperations<String, Object> setOperations;
    @Resource(name = "redisTemplate")
    private ZSetOperations<String, Object> zSetOperations;

    /**
     * 判断一个对象是否是基本类型或基本类型的封装类型，或者是String
     */
    private boolean checkClass(Object obj) {
        return obj instanceof String
                || obj instanceof Integer
                || obj instanceof Long
                || obj instanceof Character
                || obj instanceof Byte
                || obj instanceof Float
                || obj instanceof Double
                || obj instanceof Boolean;
    }

    private boolean checkClass(Class obj) {
        return obj == String.class
                || obj == Integer.class
                || obj == Long.class
                || obj == Character.class
                || obj == Byte.class
                || obj == Float.class
                || obj == Double.class
                || obj == Boolean.class;
    }

    /**
     * 存储对象：自动转为 json
     * @param key       key
     * @param value     缓存值
     * @param expire    过期时间（单位 秒）
     */
    public void setValue(String key, Object value, long expire) {
        setValue(key, value, expire, TimeUnit.SECONDS);
    }

    public void setValue(String key, Object value, long expire, TimeUnit timeUnit) {
        if (StringUtils.isEmpty(key) || value == null) {
            return;
        }
        if (checkClass(value)) {
            valueOperations.set(key, value);
        } else {
            valueOperations.set(key, toJson(value));
        }
        if (timeUnit == null) {
            timeUnit = TimeUnit.SECONDS;
        }
        if (expire != -1) {
            redisTemplate.expire(key, expire, timeUnit);
        }
    }

    public <T> T getValue(String key, Class<T> clazz) {
        checkKey(key);
        if (checkClass(clazz)) {
            return (T) valueOperations.get(key);
        }
        String json = (String) valueOperations.get(key);
        if (!StringUtils.isEmpty(json)) {
            return JsonUtil.parse(json, clazz);
        }
        return null;
    }

    /**
     * 存储数组类型 ： 自动转换json
     * @param key       key
     * @param array     集合数据
     * @param expire    过期时间（单位 s）
     * @param <T>       集合泛型
     */
    public <T> void setArray(String key, List<T> array, long expire) {
        setArray(key, array, expire, TimeUnit.SECONDS);
    }

    public <T> void setArray(String key, List<T> array, long expire, TimeUnit timeUnit) {
        if (StringUtils.isEmpty(key) || array == null) {
            return;
        }
        if (CollectionUtils.isEmpty(array)) {
            return;
        }
        valueOperations.set(key, JsonUtil.toJson(array));
        if (timeUnit == null) {
            timeUnit = TimeUnit.SECONDS;
        }
        if (expire != -1) {
            redisTemplate.expire(key, expire, timeUnit);
        }

    }

    public <T> List<T> getArray(String key, Class<T> clazz) {
        checkKey(key);
        return JsonUtil.parseToArray((String) valueOperations.get(key), clazz);
    }

    public boolean deleteKey(String key) {
        checkKey(key);
        return redisTemplate.delete(key);
    }

    private void checkKeyValue(String key, Object value) {
        checkKey(key);
        if (value == null) {
            throw new IllegalArgumentException("value is not null");
        }
    }

    private void checkKey(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key is not null");
        }
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return JsonUtil.toJson(object);
    }


////////////////////////////////////////hash start//////////////////////////////////////////////

    /**
     * 设置hash数据结构
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void setHash(String key, Object hashKey, Object value) {
        setHash(key, hashKey, value, -1L, TimeUnit.SECONDS);
    }

    public void setHash(String key, Object hashKey, Object value, Long expire) {
        setHash(key, hashKey, value, expire, TimeUnit.SECONDS);
    }

    public void setHash(String key, Object hashKey, Object value, Long expire, TimeUnit timeUnit) {
        if (StringUtils.isEmpty(key) || hashKey == null || value == null) {
            throw new IllegalArgumentException("非法参数！！！");
        }
        hashOperations.put(key, String.valueOf(hashKey), value);
        if (timeUnit == null) {
            timeUnit = TimeUnit.SECONDS;
        }
        if (expire != -1) {
            redisTemplate.expire(key, expire, timeUnit);
        }
    }

    /**
     * 不存在则put
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void putIfAbsentHash(String key, Object hashKey, Object value) {
        if (StringUtils.isEmpty(key) || hashKey == null || value == null) {
            throw new IllegalArgumentException("非法参数！！！");
        }
        hashOperations.putIfAbsent(key, String.valueOf(hashKey), value);
    }

    /**
     * 获得hash数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object getHash(String key, String hashKey) {
        if (StringUtils.isEmpty(key) || hashKey == null) {
            throw new IllegalArgumentException("非法参数！！！");
        }
        return hashOperations.get(key, hashKey);
    }

    public void deleteHash(String key, String hashKey) {
        if (StringUtils.isEmpty(key) || hashKey == null) {
            throw new IllegalArgumentException("非法参数！！！");
        }
        hashOperations.delete(key, hashKey);
    }

    public Map<String, Object> getAllHash(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("非法参数！！！");
        }
        return hashOperations.entries(key);
    }

    /**
     * 获取所有的hash数据结构的
     *
     * @param key
     * @return
     */
    public List<Object> getAllHashValue(String key) {
        checkKey(key);
        Map<String, Object> map = getAllHash(key);
        if (!map.isEmpty()) {
            Collection<Object> collection = map.values();
            return new ArrayList<Object>(collection);
        }
        return new ArrayList<Object>();
    }


////////////////////////////////////////hash end//////////////////////////////////////////////

    /**
     * pExpire 毫秒 分布式锁
     *
     * @param key
     * @param pExpire
     * @return
     */
    public boolean lock(String key, Long pExpire) {
        return (Boolean) redisTemplate.execute((RedisCallback) connection -> {
            try {
                long expireAt = System.currentTimeMillis() + pExpire + 1;
                Boolean acquire = connection.setNX(key.getBytes(), String.valueOf(expireAt).getBytes());
                if (acquire) {
                    connection.pExpire(key.getBytes(), pExpire);
                    return true;
                } else {
                    byte[] value = connection.get(key.getBytes());
                    if (Objects.nonNull(value) && value.length > 0) {
                        long expireTime = Long.parseLong(new String(value));
                        if (expireTime < System.currentTimeMillis()) {
                            connection.set(key.getBytes(), String.valueOf(System.currentTimeMillis() + pExpire + 1).getBytes());
                            connection.pExpire(key.getBytes(), pExpire);
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                connection.del(key.getBytes());
            }
            return false;
        });
    }
}
