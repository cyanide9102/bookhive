package com.cyanide9102.orderservice.order.service;

import com.cyanide9102.orderservice.order.dto.OrderRequest;
import com.cyanide9102.orderservice.order.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request, String userId);

    OrderResponse getOrderById(String orderId);

    List<OrderResponse> getOrdersByUser(String userId);
}
