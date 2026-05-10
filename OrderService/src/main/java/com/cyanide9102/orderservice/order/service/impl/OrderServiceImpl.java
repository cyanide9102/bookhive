package com.cyanide9102.orderservice.order.service.impl;

import com.cyanide9102.orderservice.annotation.RequiresLogin;
import com.cyanide9102.orderservice.client.CatalogClient;
import com.cyanide9102.orderservice.client.dto.BookResponse;
import com.cyanide9102.orderservice.common.exception.InsufficientStockException;
import com.cyanide9102.orderservice.common.exception.ResourceNotFoundException;
import com.cyanide9102.orderservice.context.RequestContext;
import com.cyanide9102.orderservice.order.Order;
import com.cyanide9102.orderservice.order.OrderMapper;
import com.cyanide9102.orderservice.order.OrderRepository;
import com.cyanide9102.orderservice.order.OrderStatus;
import com.cyanide9102.orderservice.order.dto.OrderRequest;
import com.cyanide9102.orderservice.order.dto.OrderResponse;
import com.cyanide9102.orderservice.order.service.OrderService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiresLogin
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

        BookResponse book = getBookWithRetry(request.getBookId());

        if (book.getStockQuantity() < request.getQuantity()) {
            throw new InsufficientStockException("Not enough stock was available at the time of your request!", book.getId().toString(), request.getQuantity(), book.getStockQuantity());
        }

        BigDecimal total = book.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

        Order order = Order.builder().bookId(book.getId()).bookTitle(book.getTitle()).quantity(request.getQuantity()).totalPrice(total).status(OrderStatus.CREATED).build();
        order = orderRepository.save(order);

        catalogClient.reserveStock(book.getId(), request.getQuantity());

        return orderMapper.fromEntity(order);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderResponse getOrderById(UUID id) {

        Order order = getOrder(id);
        return orderMapper.fromEntity(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByUser() {

        String userId = requestContext.userId();

        List<Order> orders = orderRepository.findByCreatedBy(userId);
        return orders.stream().map(orderMapper::fromEntity).toList();
    }

    // Surgical Retry: Marked public so the Spring Proxy can intercept it
    @Retry(name = "catalog-service")
    public BookResponse getBookWithRetry(UUID bookId) {
        return catalogClient.getBookById(bookId);
    }

    private Order getOrder(UUID id) {

        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found!", Order.class.getSimpleName(), id.toString()));
    }
}
