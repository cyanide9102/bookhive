package com.cyanide9102.common.event.order;

import java.math.BigDecimal;

public record SharedEventBook(

        String bookId,

        String bookTitle,

        Short quantity,

        BigDecimal price

) {
}
