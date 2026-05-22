package com.cyanide9102.paymentservice.consumer;

import com.cyanide9102.common.event.order.OrderCreatedEvent;
import com.cyanide9102.common.event.payment.PaymentFailedEvent;
import com.cyanide9102.common.kafka.KafkaTopics;
import com.cyanide9102.paymentservice.payment.service.PaymentService;
import com.cyanide9102.paymentservice.producer.PaymentEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final PaymentEventProducer producer;
    private final PaymentService paymentService;

    @KafkaListener(topics = KafkaTopics.ORDER_CREATED)
    public void onOrderCreated(OrderCreatedEvent event) {

        log.info("Received OrderCreatedEvent for Order ID: {}, Tracking ID: {}", event.getOrderId(), event.getTrackingId());
        try {
            OrderCreatedCommand command = OrderCreatedCommand.builder().orderId(event.getOrderId()).userId(event.getUserId()).trackingId(event.getTrackingId()).totalAmount(event.getTotalAmount()).paymentToken(event.getPaymentToken()).build();
            paymentService.processOrderPayment(command);
        } catch (Exception e) {
            PaymentFailedEvent paymentFailedEvent = PaymentFailedEvent.builder().orderId(event.getOrderId()).userId(event.getUserId()).trackingId(event.getTrackingId()).totalAmount(event.getTotalAmount()).message(e.getMessage()).build();
            producer.publishPaymentFailedEvent(paymentFailedEvent);
        }
    }
}
