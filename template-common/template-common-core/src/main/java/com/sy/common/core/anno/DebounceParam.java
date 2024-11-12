package com.sy.common.core.anno;

import java.lang.annotation.*;

/**
 * @author Monster
 * @version v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Documented
public @interface DebounceParam {
}
