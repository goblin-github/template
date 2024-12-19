package com.sy.common.core.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Monster
 * @version v1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseRequestPageParam extends BaseRequestParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 页码
     */
    @Schema(description = "页码")
    private Integer pageNum = 1;
    /**
     * 每页数量
     */
    @Schema(description = "页面大小")
    private Integer pageSize = 10;
}
