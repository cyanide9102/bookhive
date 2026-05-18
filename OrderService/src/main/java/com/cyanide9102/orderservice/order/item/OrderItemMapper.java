package com.cyanide9102.orderservice.order.item;

import com.cyanide9102.orderservice.order.item.dto.OrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {

    OrderItemResponse fromEntity(OrderItem item);
}

