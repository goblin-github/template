package com.sy.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Monster
 * @version v1.0
 */
@TableName(value = "bus_cash_order")
@Data
public class CashOrder {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 提现者ID
     */
    private Long playerId;
    /**
     * 渠道名
     */
    private String appName;
    /**
     * 提现值
     */
    private BigDecimal cashValue;
    /**
     * 支付渠道
     */
    private String channel;
    /**
     * 支付用户名字
     */
    private String username;
    /**
     * 支付用户手机号
     */
    private String mobile;
    /**
     * 卡号
     */
    private String cardNumber;
    /**
     * 巴西税号
     */
    private String taxNumber;
    /**
     * 备注
     */
    private String description;
    /**
     * 审核原因
     */
    private String remark;
    /**
     * 平台订单ID
     */
    private String platformOrderId;
    /**
     * 状态 0:待审核 1:撤销 2:审核未通过 3：待到账 4：已到账 5：未到账
     */
    private Integer status;
    /**
     * 删除标识 0:未删除 1:已删除
     */
    private Integer deleted;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;


}
