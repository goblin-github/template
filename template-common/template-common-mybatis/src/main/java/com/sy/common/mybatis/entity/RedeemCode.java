package com.sy.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Monster
 * @version v1.0
 */
@TableName(value = "bus_redeem_code", autoResultMap = true)
@Data
public class RedeemCode {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 兑换码
     */
    private String redeemCode;
    /**
     * 激活时间
     */
    private LocalDateTime activeTime;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
    /**
     * 兑换限制
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private RedeemLimit redeemLimit;
    /**
     * 兑换信息
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private RedeemInfo redeemInfo;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 删除标识
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

    @Data
    public static class RedeemLimit {
        /**
         * 兑换总次数
         */
        private Integer redeemCount;
        /**
         * 玩家vip等级
         */
        private Integer playerVipLevel;
        /**
         * 玩家兑换次数
         */
        private Integer playerRedeemCount;
        /**
         * 游戏渠道列表
         */
        private List<String> appNames;
        /**
         * 注册时间在兑换码激活时间之后
         */
        private Boolean playerRegisterTimeAfterActiveTime;
    }

    @Data
    public static class RedeemInfo {
        private Integer gold;
    }
}
