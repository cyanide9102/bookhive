package com.cyanide9102.orderservice.order;

import com.cyanide9102.orderservice.order.dto.OrderRequest;
import com.cyanide9102.orderservice.order.dto.OrderResponse;
import com.cyanide9102.orderservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {

        return orderService.createOrder(orderRequest);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable String id) {

        return orderService.getOrderById(id);
    }

    @GetMapping()
    public List<OrderResponse> getOrdersByUserId() {

        return orderService.getOrdersByUser();
    }
}
