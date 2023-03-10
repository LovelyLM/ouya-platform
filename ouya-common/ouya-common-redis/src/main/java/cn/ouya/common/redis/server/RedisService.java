package cn.ouya.common.redis.server;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 *
 * @author yuzhiheng
 **/
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisService {

    @Resource
    public RedisTemplate redisTemplate;

    /**
     * 判断 key 是否存在
     *
     * @param key 键
     * @return true=存在；false=不存在
     */
    public Boolean hasKey(String key) {

        return redisTemplate.hasKey(key);
    }

    /**
     * 删除单个对象
     *
     * @param key 键
     */
    public Boolean deleteObject(final String key) {

        return redisTemplate.delete(key);
    }

    /**
     * 设置有效时间
     *
     * @param key     键
     * @param timeout 有效时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public Boolean expire(final String key, final long timeout, final TimeUnit unit) {

        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * String：写入值
     *
     * @param key   键
     * @param value 值
     */
    public <T> void setCacheObject(final String key, final T value) {

        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * String：写入值并设置有效时间
     *
     * @param key      键
     * @param value    值
     * @param timeout  有效时间
     * @param timeUnit 时间单位
     */
    public <T> void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {

        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * String：获取值
     *
     * @param key 键
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key, Class<T> clazz) {

        Object object = redisTemplate.opsForValue().get(key);

        if (object == null) {
            return null;
        }

        if (clazz.isPrimitive() || ClassUtil.isPrimitiveWrapper(clazz)) {
            return BeanUtil.toBean(object, clazz);
        } else {
            return JSONUtil.toBean(object.toString(), clazz);
        }
    }

    /**
     * Hash：批量写入Hash键值
     *
     * @param key     键
     * @param dataMap Hash键值的Map
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {

        if (MapUtil.isNotEmpty(dataMap)) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * Hash：写入值
     *
     * @param key   键
     * @param hKey  Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {

        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * Hash：根据key获取的所有Hash键值
     *
     * @param key   键
     * @param clazz 返回value的类型
     */
    public <T> Map<String, T> getCacheMap(final String key, Class<T> clazz) {

        Map<String, Object> objectMap = redisTemplate.<String, Object>opsForHash().entries(key);

        Map<String, T> map = new LinkedHashMap<>();

        if (MapUtil.isEmpty(objectMap)) {
            return map;
        }

        objectMap.forEach((hKey, value) -> {
            if (value != null) {
                if (clazz.isPrimitive() || ClassUtil.isPrimitiveWrapper(clazz)) {
                    map.put(hKey, BeanUtil.toBean(value, clazz));
                } else {
                    map.put(hKey, JSONUtil.toBean(value.toString(), clazz));
                }
            }
        });

        return map;
    }

    /**
     * Hash：获取值
     *
     * @param key   键
     * @param hKey  Hash键
     * @param clazz 返回value的类型
     */
    public <T> T getCacheMapValue(final String key, final String hKey, Class<T> clazz) {

        Object object = redisTemplate.<String, Object>opsForHash().get(key, hKey);

        if (object == null) {
            return null;
        }
        if (clazz.isPrimitive() || ClassUtil.isPrimitiveWrapper(clazz)) {
            return BeanUtil.toBean(object, clazz);
        } else {
            return JSONUtil.toBean(object.toString(), clazz);
        }
    }

    /**
     * Hash：获取多个Hash键的值
     *
     * @param key      键
     * @param hKeyList Hash键集合
     * @param clazz    返回value的类型
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection hKeyList, Class<T> clazz) {

        List<Object> objectList = redisTemplate.opsForHash().multiGet(key, CollUtil.distinct(hKeyList));

        List<T> list = new ArrayList<>();

        if (CollUtil.isEmpty(objectList)) {
            return new ArrayList<>();
        }

        for (Object object : objectList) {
            if (object != null) {
                if (clazz.isPrimitive() || ClassUtil.isPrimitiveWrapper(clazz)) {
                    list.add(BeanUtil.toBean(object, clazz));
                } else {
                    list.add(JSONUtil.toBean(object.toString(), clazz));
                }
            }
        }

        return list;
    }

    /**
     * Hash：根据键和Hash键移除元素
     *
     * @param key      需要删除的键
     * @param hKeyList 需要删除的Hash键集合
     */
    public Long deleteCacheMap(final String key, final List<String> hKeyList) {

        return redisTemplate.opsForHash().delete(key, hKeyList.toArray());
    }

    /**
     * Set：写入值
     *
     * @param key     键
     * @param dataSet 值
     */
    public <T> Long setCacheSet(final String key, final Set<T> dataSet) {

        return redisTemplate.opsForSet().add(key, dataSet.toArray());
    }

    /**
     * Set：根据key获取Set的所有值
     *
     * @param key   键
     * @param clazz 返回value的类型
     */
    public <T> Set<T> getCacheSet(final String key, Class<T> clazz) {

        Set<Object> objectSet = redisTemplate.opsForSet().members(key);

        Set<T> set = new LinkedHashSet();
        if (CollUtil.isEmpty(objectSet)) {
            return set;
        }

        objectSet.forEach(object -> {
            if (object != null) {
                if (clazz.isPrimitive() || ClassUtil.isPrimitiveWrapper(clazz)) {
                    set.add(BeanUtil.toBean(object, clazz));
                } else {
                    set.add(JSONUtil.toBean(object.toString(), clazz));
                }
            }
        });

        return set;
    }

    /**
     * Set：根据键和值移除元素
     *
     * @param key     需要删除的键
     * @param dataSet 需要删除的value集合
     */
    public <T> Long deleteCacheSet(final String key, final Set<T> dataSet) {

        return redisTemplate.opsForSet().remove(key, dataSet.toArray());
    }

}
