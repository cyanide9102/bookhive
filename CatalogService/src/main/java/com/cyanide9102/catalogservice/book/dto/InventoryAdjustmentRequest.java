package com.cyanide9102.catalogservice.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryAdjustmentRequest {

    @NotBlank
    private String bookId;

    @NotBlank
    @Min(1)
    private Short quantity;
}
