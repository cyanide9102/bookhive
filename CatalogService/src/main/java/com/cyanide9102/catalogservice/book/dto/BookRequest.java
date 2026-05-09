package com.cyanide9102.catalogservice.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {

    @NotBlank
    private String isbn;

    @NotBlank
    private String title;

    @NotNull
    @PositiveOrZero
    private Short stockQuantity;

    @NotNull
    @Positive
    private BigDecimal price;

    private UUID categoryId;
}
