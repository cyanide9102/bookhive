package com.cyanide9102.paymentservice.consumer;

import com.cyanide9102.common.event.order.OrderCreatedEvent;
import com.cyanide9102.common.kafka.KafkaTopics;
import com.cyanide9102.paymentservice.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPlacedConsumer {

    private final PaymentService paymentService;

    @KafkaListener(topics = KafkaTopics.ORDER_CREATED, groupId = "payment-group")
    public void consumeOrderPlacedEvent(OrderCreatedEvent event) {

        log.info("Received OrderPlacedEvent for Order ID: {}", event.getOrderId());
        try {
            // TODO: implement order processing in payment server
        } catch (Exception e) {
            log.error("Failed to process payment for Order ID: {}", event.getOrderId(), e);
            // TODO: Handle error strategy / Dead Letter Queue (DQL) if necessary
        }
    }
}
