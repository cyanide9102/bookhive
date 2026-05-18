package com.cyanide9102.orderservice.order.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {

    private String id;
    private String bookId;
    private String bookTitle;
    private Short quantity;
    private BigDecimal price;
}
