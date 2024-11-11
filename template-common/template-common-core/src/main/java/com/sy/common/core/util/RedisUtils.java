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

    private static final RedissonClient CLIENT;

    private static final StringRedisTemplate REDIS_TEMPLATE;

    static {
        CLIENT = SpringUtils.getContext().getBean(RedissonClient.class);
        REDIS_TEMPLATE = SpringUtils.getContext().getBean(StringRedisTemplate.class);
    }

    public static <T> RScoredSortedSet<T> getScoredSortedSet(String key) {
        return CLIENT.getScoredSortedSet(key);
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
        RBatch batch = CLIENT.createBatch();
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
        RBucket<T> rBucket = CLIENT.getBucket(key);
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

    /**
     * 限流
     *
     * @param bizKey 限流key
     * @param count  速率
     * @param time   速率间隔
     * @return 是否通过限流
     */
    public static boolean rateLimiter(String bizKey, long count, long time) {
        RRateLimiter rateLimiter = getRateLimiter(bizKey, count, time);
        return rateLimiter.tryAcquire();
    }

    /**
     * 获取业务限流器
     *
     * @param bizKey 业务key
     * @param count  速率
     * @param time   速率间隔
     * @return 业务限流器
     */
    private static RRateLimiter getRateLimiter(String bizKey, long count, long time) {
        String limiterKey = getLimiterKey(bizKey);
        RRateLimiter rateLimiter = CLIENT.getRateLimiter(limiterKey);
        if (!rateLimiter.isExists()) {
            rateLimiter.trySetRate(RateType.OVERALL, count, time, RateIntervalUnit.SECONDS);
        }
        // 获取限流的配置信息
        RateLimiterConfig rateLimiterConfig = rateLimiter.getConfig();
        // 上次配置的限流时间毫秒值
        Long rateInterval = rateLimiterConfig.getRateInterval();
        // 上次配置的限流次数
        Long rate = rateLimiterConfig.getRate();
        // 将timeOut转换成毫秒之后再跟rateInterval进行比较
        if (TimeUnit.MILLISECONDS.convert(time, TimeUnit.SECONDS) != rateInterval || count != rate) {
            // 如果rateLimiterConfig的配置跟我们注解上面的值不一致，说明服务器重启过，程序员又修改了限流的配置
            // 删除原有配置
            rateLimiter.delete();
            // 以程序员重启后的限流配置为准，重新设置
            rateLimiter.trySetRate(RateType.OVERALL, count, time, RateIntervalUnit.SECONDS);
        }
        return rateLimiter;
    }

    /**
     * 获取限流器key
     *
     * @param bizKey 业务key
     * @return 限流器key
     */
    private static String getLimiterKey(String bizKey) {
        return RATE_LIMITER_KEY + bizKey;
    }
}
