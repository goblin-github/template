package com.sy.demo.util;


import com.sy.common.mybatis.entity.CashOrder;
import com.sy.demo.pojo.CashOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Monster
 * @version v1.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapperStructUtils {

    MapperStructUtils INSTANCE = Mappers.getMapper(MapperStructUtils.class);

    @Mapping(target = "acquirerId", source = "playerId")
    CashOrderDTO convert(CashOrder cashOrder);
}
