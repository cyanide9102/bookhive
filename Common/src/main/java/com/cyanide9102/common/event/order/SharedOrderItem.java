package com.cyanide9102.common.event.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharedOrderItem {

    private String bookId;
    private String bookTitle;
    private Short quantity;
    private BigDecimal price;
}
