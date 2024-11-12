package com.sy.common.core.anno;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Monster
 * @version v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface Debounce {
    /**
     * 锁的前缀
     *
     */
    String prefix() default "";

    /**
     * 过期时间
     *
     */
    int expire() default 2;

    /**
     * 过期单位
     *
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * key的分隔符
     *
     */
    String delimiter() default ":";
}
