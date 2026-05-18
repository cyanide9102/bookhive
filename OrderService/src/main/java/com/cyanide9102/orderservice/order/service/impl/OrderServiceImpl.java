package com.cyanide9102.orderservice.order.service.impl;

import com.cyanide9102.common.annotation.RequiresLogin;
import com.cyanide9102.common.context.RequestContext;
import com.cyanide9102.common.exception.ResourceNotFoundException;
import com.cyanide9102.orderservice.client.CatalogClient;
import com.cyanide9102.orderservice.client.dto.BookResponse;
import com.cyanide9102.orderservice.client.dto.InventoryAdjustmentRequest;
import com.cyanide9102.orderservice.order.Order;
import com.cyanide9102.orderservice.order.OrderMapper;
import com.cyanide9102.orderservice.order.OrderRepository;
import com.cyanide9102.orderservice.order.OrderStatus;
import com.cyanide9102.orderservice.order.dto.OrderRequest;
import com.cyanide9102.orderservice.order.dto.OrderResponse;
import com.cyanide9102.orderservice.order.item.OrderItem;
import com.cyanide9102.orderservice.order.item.dto.OrderItemRequest;
import com.cyanide9102.orderservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiresLogin
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final RequestContext requestContext;

    private final CatalogClient catalogClient;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest request) {

        Optional<Order> existingOrder = orderRepository.findByTrackingId(request.getTrackingId());
        if (existingOrder.isPresent()) {
            log.info("Duplicate request intercepted for trackingId: {}. Returning existing order state.", request.getTrackingId());
            return orderMapper.fromEntity(existingOrder.get());
        }

        List<InventoryAdjustmentRequest> reserveRequest = request.getItems().stream().map(item -> new InventoryAdjustmentRequest(item.getBookId(), item.getQuantity())).toList();
        List<BookResponse> books = catalogClient.reserveStock(reserveRequest);

        Order order = Order.builder().totalPrice(BigDecimal.ZERO).status(OrderStatus.CREATED).trackingId(request.getTrackingId()).items(new ArrayList<>()).build();

        BigDecimal total = BigDecimal.ZERO;
        for (BookResponse book : books) {
            Short quantity = request.getItems().stream().filter(item -> item.getBookId().equals(book.getId())).findFirst().map(OrderItemRequest::getQuantity).orElse((short) 1);
            BigDecimal subTotal = book.getPrice().multiply(BigDecimal.valueOf(quantity));
            total = total.add(subTotal);

            OrderItem item = OrderItem.builder().order(order).bookId(book.getId()).bookTitle(book.getTitle()).quantity(quantity).price(book.getPrice()).build();
            order.getItems().add(item);
        }

        order.setTotalPrice(total);
        order = orderRepository.save(order);

        // TODO: Publish OrderPlaced event

        return orderMapper.fromEntity(order);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderResponse getOrderById(String id) {

        Order order = getOrder(id);
        return orderMapper.fromEntity(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByUser() {

        String userId = requestContext.userId();

        List<Order> orders = orderRepository.findByCreatedBy(userId);
        return orders.stream().map(orderMapper::fromEntity).toList();
    }

    private Order getOrder(String id) {

        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found!", Order.class.getSimpleName(), id));
    }
}
