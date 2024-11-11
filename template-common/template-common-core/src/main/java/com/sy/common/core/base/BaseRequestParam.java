package com.sy.common.core.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Monster
 * @version v1.0
 */
@Data
public class BaseRequestParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 请求时间戳
     */
    @Schema(description = "请求时间戳")
    private Long timestamp;
}
