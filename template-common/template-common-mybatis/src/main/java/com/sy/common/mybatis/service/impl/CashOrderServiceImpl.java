package com.sy.common.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.common.mybatis.entity.CashOrder;
import com.sy.common.mybatis.mapper.CashOrderMapper;
import com.sy.common.mybatis.service.CashOrderService;
import org.springframework.stereotype.Service;

/**
 * @author Monster
 * @version v1.0
 */
@Service
public class CashOrderServiceImpl extends ServiceImpl<CashOrderMapper, CashOrder> implements CashOrderService {
}
