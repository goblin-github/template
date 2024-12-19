package com.sy.common.core.anno;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 时间窗口限流注解
 *
 * @author Monster
 * @version v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface RateLimit {
    /**
     * 限制时间窗口间隔长度
     */
    int times() default 2;

    /**
     * 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 上述时间窗口内允许的最大请求数量
     */
    int maxCount() default 5;
}
