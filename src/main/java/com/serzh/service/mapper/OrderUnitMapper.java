package com.serzh.service.mapper;

import com.serzh.domain.OrderUnit;
import com.serzh.service.dto.OrderUnitDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderUnitMapper {

    @Mapping(source = "item.name", target = "menu")
    OrderUnitDTO orderUnitToOrderUnitDTO(OrderUnit orderUnit);

    Set<OrderUnitDTO> orderUnitsToOrderUnitDTOs(Set<OrderUnit> items);

}
