package com.sy.common.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sy.common.mybatis.entity.CashOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author Monster
 * @version v1.0
 */
@Mapper
public interface CashOrderMapper extends BaseMapper<CashOrder> {
    /**
     * 获取某玩家的总提现金额
     *
     * @param playerId 玩家ID
     * @return 总提现金额
     */
    BigDecimal statPlayerCashAll(@Param("playerId") long playerId);


}
