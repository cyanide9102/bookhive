package com.cyanide9102.orderservice.order;

import com.cyanide9102.common.annotation.RequiresLogin;
import com.cyanide9102.common.context.RequestContext;
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

    private final RequestContext requestContext;
    private final OrderService orderService;

    @RequiresLogin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {

        return orderService.createOrder(orderRequest, requestContext.userId());
    }

    @RequiresLogin
    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable String id) {

        return orderService.getOrderById(id);
    }

    @RequiresLogin
    @GetMapping()
    public List<OrderResponse> getOrdersByUserId() {

        return orderService.getOrdersByUser(requestContext.userId());
    }
}
