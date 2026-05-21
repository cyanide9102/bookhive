package com.cyanide9102.common.event.order;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SharedEventBook {

    private String bookId;
    private String bookTitle;
    private Short quantity;
    private BigDecimal price;
}
