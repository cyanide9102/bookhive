package com.cyanide9102.orderservice.order.service.impl;

import com.cyanide9102.common.event.order.OrderCompletedEvent;
import com.cyanide9102.common.event.order.OrderCreatedEvent;
import com.cyanide9102.common.event.order.SharedOrderItem;
import com.cyanide9102.common.exception.ResourceNotFoundException;
import com.cyanide9102.orderservice.client.CatalogClient;
import com.cyanide9102.orderservice.client.dto.BookResponse;
import com.cyanide9102.orderservice.client.dto.InventoryAdjustmentRequest;
import com.cyanide9102.orderservice.consumer.command.PaymentCompletedCommand;
import com.cyanide9102.orderservice.consumer.command.PaymentFailedCommand;
import com.cyanide9102.orderservice.order.Order;
import com.cyanide9102.orderservice.order.OrderMapper;
import com.cyanide9102.orderservice.order.OrderRepository;
import com.cyanide9102.orderservice.order.OrderStatus;
import com.cyanide9102.orderservice.order.dto.OrderRequest;
import com.cyanide9102.orderservice.order.dto.OrderResponse;
import com.cyanide9102.orderservice.order.item.OrderItem;
import com.cyanide9102.orderservice.order.item.dto.OrderItemRequest;
import com.cyanide9102.orderservice.order.service.OrderService;
import com.cyanide9102.orderservice.producer.OrderEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CatalogClient catalogClient;

    private final OrderEventProducer producer;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest request, String userId) {

        Optional<Order> existingOrder = orderRepository.findByTrackingId(request.getTrackingId());
        if (existingOrder.isPresent()) {
            log.info("Duplicate request intercepted for trackingId: {}. Returning existing order state.", request.getTrackingId());
            return orderMapper.fromEntity(existingOrder.get());
        }

        List<InventoryAdjustmentRequest> reserveRequest = request.getItems().stream().map(item -> new InventoryAdjustmentRequest(item.getBookId(), item.getQuantity())).toList();
        List<BookResponse> books = catalogClient.reserveStock(reserveRequest);

        Order order = Order.builder().totalPrice(BigDecimal.ZERO).status(OrderStatus.CREATED).trackingId(request.getTrackingId()).items(new ArrayList<>()).build();

        BigDecimal total = BigDecimal.ZERO;
        List<SharedOrderItem> sharedOrderItems = new ArrayList<>();

        Map<String, Short> bookQuantityMap = request.getItems().stream().collect(Collectors.toMap(OrderItemRequest::getBookId, OrderItemRequest::getQuantity));
        for (BookResponse book : books) {
            Short quantity = bookQuantityMap.getOrDefault(book.getId(), (short) 1);
            BigDecimal subTotal = book.getPrice().multiply(BigDecimal.valueOf(quantity));
            total = total.add(subTotal);

            OrderItem item = OrderItem.builder().order(order).bookId(book.getId()).bookTitle(book.getTitle()).quantity(quantity).price(book.getPrice()).build();
            order.getItems().add(item);

            SharedOrderItem sharedOrderItem = SharedOrderItem.builder().bookId(book.getId()).bookTitle(book.getTitle()).quantity(quantity).price(book.getPrice()).build();
            sharedOrderItems.add(sharedOrderItem);
        }

        order.setTotalPrice(total);
        order.setUserId(userId);

        order = orderRepository.save(order);

        log.info("Publishing OrderPlacedEvent for orderId={}, trackingId={}", order.getId(), order.getTrackingId());
        OrderCreatedEvent event = OrderCreatedEvent.builder().trackingId(request.getTrackingId()).orderId(order.getId()).totalAmount(total).userId(order.getUserId()).items(sharedOrderItems).paymentToken(request.getPaymentToken()).build();
        producer.publishOrderCreatedEvent(event);

        return orderMapper.fromEntity(order);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderResponse getOrderById(String id) {

        Order order = getOrder(id);
        return orderMapper.fromEntity(order);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderResponse> getOrdersByUser(String userId) {

        List<Order> orders = orderRepository.findByCreatedBy(userId);
        return orders.stream().map(orderMapper::fromEntity).toList();
    }

    @Transactional
    @Override
    public void processPaymentCompleted(PaymentCompletedCommand command) {

        Order order = getOrder(command.getOrderId());
        if (order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.CANCELLED) {
            return;
        }

        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);

        OrderCompletedEvent event = OrderCompletedEvent.builder().paymentId(command.getPaymentId()).orderId(order.getId()).userId(order.getUserId()).trackingId(order.getTrackingId()).totalAmount(order.getTotalPrice()).items(order.getItems().stream().map(item -> new SharedOrderItem(item.getBookId(), item.getBookTitle(), item.getQuantity(), item.getPrice())).toList()).build();
        producer.publishOrderCompletedEvent(event);
    }

    @Transactional
    @Override
    public void processPaymentFailed(PaymentFailedCommand command) {

        Order order = getOrder(command.getOrderId());
        if (order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.CANCELLED) {
            return;
        }

        if (order.getStatus() == OrderStatus.FAILED) {
            log.info("Order already marked FAILED for Tracking ID: {}", command.getTrackingId());
            return;
        }

        List<InventoryAdjustmentRequest> releaseRequest = order.getItems().stream().map(item -> new InventoryAdjustmentRequest(item.getBookId(), item.getQuantity())).toList();
        catalogClient.releaseStock(releaseRequest);

        order.setStatus(OrderStatus.FAILED);
        order.setFailureMessage(command.getMessage());
        orderRepository.save(order);
    }

    private Order getOrder(String id) {

        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found!", Order.class.getSimpleName(), id));
    }
}
