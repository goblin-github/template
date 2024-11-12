package com.sy.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sy.common.core.anno.Debounce;
import com.sy.common.core.anno.DebounceParam;
import com.sy.common.core.base.Result;
import com.sy.demo.pojo.CashOrderDTO;
import com.sy.demo.pojo.QueryCashOrderDTO;
import com.sy.demo.service.IBusCashOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author Monster
 * @version v1.0
 */
@RestController
@RequestMapping("cash/order")
@Tag(name = "提现订单相关接口")
@Slf4j
public class DemoCashController {
    @Resource
    private IBusCashOrderService busCashOrderService;

    @PostMapping("query")
    public Result<IPage<CashOrderDTO>> query(@RequestBody QueryCashOrderDTO queryParam) {
        return Result.success(busCashOrderService.query(queryParam));
    }

    @GetMapping("player/total")
    @Debounce(expire = 10)
    public Result<BigDecimal> getPlayerCashTotal(@RequestParam @DebounceParam long playerId) {
        try {
            Thread.sleep(19000);
        } catch (Exception e) {
            log.error("123");
        }
        BigDecimal playerCashTotal = busCashOrderService.getPlayerCashTotal(playerId);
        log.error("12344");
        return Result.success(playerCashTotal);
//        throw new BusinessException(BaseRCodeEnum.PARAM_ERROR);
    }
}
