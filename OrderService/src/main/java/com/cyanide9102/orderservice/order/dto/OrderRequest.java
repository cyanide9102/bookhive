package com.cyanide9102.orderservice.order.dto;

import com.cyanide9102.orderservice.order.item.dto.OrderItemRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotBlank
    @Size(min = 13, max = 13)
    private String trackingId;

    @NotBlank
    private String paymentToken;

    @NotEmpty
    @Valid
    private List<OrderItemRequest> items;
}
