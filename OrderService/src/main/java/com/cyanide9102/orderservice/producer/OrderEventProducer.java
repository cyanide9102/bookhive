package com.cyanide9102.orderservice.producer;

import com.cyanide9102.common.event.order.OrderCompletedEvent;
import com.cyanide9102.common.event.order.OrderCreatedEvent;
import com.cyanide9102.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishOrderCreatedEvent(OrderCreatedEvent event) {

        log.info("Publishing OrderCreatedEvent for Order ID: {}, Tracking ID: {}", event.getOrderId(), event.getTrackingId());
        kafkaTemplate.send(KafkaTopics.ORDER_CREATED, event.getTrackingId(), event);
    }

    public void publishOrderCompletedEvent(OrderCompletedEvent event) {

        log.info("Publishing OrderCompletedEvent for Order ID: {}, Tracking ID: {}", event.getOrderId(), event.getTrackingId());
        kafkaTemplate.send(KafkaTopics.ORDER_COMPLETED, event.getTrackingId(), event);
    }
}
