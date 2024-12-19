package com.sy.common.core.util;


import com.sy.common.core.base.BaseRCodeEnum;
import com.sy.common.core.exception.BusinessException;

import java.util.Objects;

/**
 * @author Monster
 * @version v1.0
 */
public class LoginHelper {

    private static final ThreadLocal<Long> USER_HOLDER = new ThreadLocal<>();

    public static void setUserId(Long playerId) {
        USER_HOLDER.set(playerId);
    }

    public static Long getUserId() {
        Long userId = USER_HOLDER.get();
        if (Objects.isNull(userId)) {
            throw new BusinessException(BaseRCodeEnum.NOT_LOGIN);
        }
        return userId;
    }

    public static void clear() {
        USER_HOLDER.remove();
    }

}
