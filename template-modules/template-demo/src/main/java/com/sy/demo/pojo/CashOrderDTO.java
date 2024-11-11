package com.sy.demo.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Monster
 * @version v1.0
 */
@Data
public class CashOrderDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "订单ID")
    private String orderId;

    @Schema(description = "支付用户名字")
    private String username;

    @Schema(description = "支付用户手机号")
    private String mobile;

    @Schema(description = "获得者id")
    private Long acquirerId;

    @Schema(description = "提现值")
    private BigDecimal cashValue;

    @Schema(description = "状态 0:待审核 1:撤销 2:审核未通过 3：待到账 4：已到账 5：未到账")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime gmtCreate;

    @Schema(description = "更新时间")
    private LocalDateTime gmtModified;

    @Schema(description = "平台订单ID")
    private String platformOrderId;

    @Schema(description = "支付渠道")
    private String channel;

    @Schema(description = "卡号")
    private String cardNumber;

    @Schema(description = "订单备注")
    private String description;

    @Schema(description = "审核原因")
    private String remark;
}
