package com.cyanide9102.orderservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {

    private String id;

    private String isbn;

    private String title;

    private Short stockQuantity;

    private BigDecimal price;

    private String categoryId;
}
