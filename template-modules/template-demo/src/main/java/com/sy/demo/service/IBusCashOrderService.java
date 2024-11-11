package com.sy.demo.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sy.demo.pojo.CashOrderDTO;
import com.sy.demo.pojo.QueryCashOrderDTO;

import java.math.BigDecimal;

/**
 * @author Monster
 * @version v1.0
 */
public interface IBusCashOrderService {

    /**
     * 查询订单列表
     *
     * @param queryParam 查询参数
     * @return 订单列表
     */
    IPage<CashOrderDTO> query(QueryCashOrderDTO queryParam);

    /**
     * 获取玩家提现总额
     *
     * @param playerId 玩家ID
     * @return 玩家提现总额
     */
    BigDecimal getPlayerCashTotal(long playerId);
}
