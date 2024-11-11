package com.sy.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sy.common.mybatis.entity.CashOrder;
import com.sy.common.mybatis.mapper.CashOrderMapper;
import com.sy.common.mybatis.service.CashOrderService;
import com.sy.demo.pojo.CashOrderDTO;
import com.sy.demo.pojo.QueryCashOrderDTO;
import com.sy.demo.service.IBusCashOrderService;
import com.sy.demo.util.MapperStructUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Monster
 * @version v1.0
 */
@Service
public class BusCashOrderService implements IBusCashOrderService {

    @Resource
    private CashOrderService cashOrderService;
    @Resource
    private CashOrderMapper cashOrderMapper;

    @Override
    public IPage<CashOrderDTO> query(QueryCashOrderDTO queryParam) {
        LambdaQueryWrapper<CashOrder> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CashOrder::getStatus, queryParam.getStatus());
        queryWrapper.orderByDesc(CashOrder::getGmtCreate);
        Page<CashOrder> cashOrderPage = cashOrderService.page(new Page<>(queryParam.getPageNum(), queryParam.getPageSize()), queryWrapper);
        return cashOrderPage.convert(MapperStructUtils.INSTANCE::convert);
    }

    @Override
    public BigDecimal getPlayerCashTotal(long playerId) {
        return cashOrderMapper.statPlayerCashAll(playerId);
    }
}
