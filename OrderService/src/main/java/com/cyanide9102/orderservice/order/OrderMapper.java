package com.cyanide9102.orderservice.order;

import com.cyanide9102.orderservice.order.dto.OrderResponse;
import com.cyanide9102.orderservice.order.item.OrderItemMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {OrderItemMapper.class})
public interface OrderMapper {

    OrderResponse fromEntity(Order order);
}
