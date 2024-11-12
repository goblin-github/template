package com.sy.common.core.util;

import org.redisson.api.*;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author Monster
 * @version v1.0
 */
public class RedisUtils {

    private static final String RATE_LIMITER_KEY = "rate:limiter:";
    public static final RedissonClient REDISSON_CLIENT;

    public static final StringRedisTemplate REDIS_TEMPLATE;

    static {
        REDISSON_CLIENT = SpringUtils.getContext().getBean(RedissonClient.class);
        REDIS_TEMPLATE = SpringUtils.getContext().getBean(StringRedisTemplate.class);
    }

    public static <T> RScoredSortedSet<T> getScoredSortedSet(String key) {
        return REDISSON_CLIENT.getScoredSortedSet(key);
    }

    public static <T> void setScoredSortedSet(String key, T value, double score) {
        RScoredSortedSet<T> scoredSortedSet = getScoredSortedSet(key);
        scoredSortedSet.add(score, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param duration 时间
     */
    public static <T> void setCacheObject(final String key, final T value, final Duration duration) {
        RBatch batch = REDISSON_CLIENT.createBatch();
        RBucketAsync<T> bucket = batch.getBucket(key);
        bucket.setAsync(value);
        bucket.expireAsync(duration);
        batch.execute();
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public static <T> T getCacheObject(final String key) {
        RBucket<T> rBucket = REDISSON_CLIENT.getBucket(key);
        return rBucket.get();
    }


    /**
     * 根据key获取值
     *
     * @param key 指定key
     * @return 对应值
     */
    public static String getCacheString(String key) {
        return key == null ? null : REDIS_TEMPLATE.opsForValue().get(key);
    }

    /**
     * 将值放入缓存
     *
     * @param key   指定key
     * @param value 值
     */
    public static void setCacheString(String key, String value) {
        REDIS_TEMPLATE.opsForValue().set(key, value);
    }

    /**
     * 将值放入缓存并设置时间
     *
     * @param key   指定key
     * @param value 值
     * @param time  时间(秒) -1为无期限
     */
    public static void setCacheString(String key, String value, long time) {
        if (time > 0) {
            REDIS_TEMPLATE.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            REDIS_TEMPLATE.opsForValue().set(key, value);
        }
    }

    public static Long getExpire(String key) {
        return REDIS_TEMPLATE.getExpire(key);
    }

    public static void setExpire(String key, long time) {
        REDIS_TEMPLATE.expire(key, time, TimeUnit.SECONDS);
    }

    public static void deleteCacheString(String key) {
        REDIS_TEMPLATE.delete(key);
    }

}
