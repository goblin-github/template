package com.sy.demo.pojo;

import com.sy.common.core.base.BaseRequestPageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Monster
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryCashOrderDTO extends BaseRequestPageParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "状态 0:待审核 1:撤销 2:审核未通过 3：待到账 4：已到账 5：未到账")
    private Integer status;
}
