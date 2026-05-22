package com.cyanide9102.orderservice.consumer;

import com.cyanide9102.common.event.payment.PaymentCompletedEvent;
import com.cyanide9102.common.event.payment.PaymentFailedEvent;
import com.cyanide9102.common.kafka.KafkaTopics;
import com.cyanide9102.orderservice.consumer.command.PaymentCompletedCommand;
import com.cyanide9102.orderservice.consumer.command.PaymentFailedCommand;
import com.cyanide9102.orderservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = KafkaTopics.PAYMENT_COMPLETED)
    public void onPaymentCompleted(PaymentCompletedEvent event) {

        log.info("Received PaymentCompletedEvent for Order ID: {}, Tracking ID: {}", event.getOrderId(), event.getTrackingId());
        try {
            PaymentCompletedCommand command = PaymentCompletedCommand.builder().paymentId(event.getPaymentId()).orderId(event.getOrderId()).userId(event.getUserId()).trackingId(event.getTrackingId()).build();
            orderService.processPaymentCompleted(command);
        } catch (Exception e) {
            log.error("Failed to process PaymentCompletedEvent for Order ID: {}, Tracking ID: {}", event.getOrderId(), event.getTrackingId());
            throw e;
        }
    }

    @KafkaListener(topics = KafkaTopics.PAYMENT_FAILED)
    public void onPaymentFailed(PaymentFailedEvent event) {

        log.info("Received PaymentFailedEvent for Order ID: {}, Tracking ID: {}, Reason: {}", event.getOrderId(), event.getTrackingId(), event.getMessage());
        try {
            PaymentFailedCommand command = PaymentFailedCommand.builder().orderId(event.getOrderId()).userId(event.getUserId()).trackingId(event.getTrackingId()).message(event.getMessage()).build();
            orderService.processPaymentFailed(command);
        } catch (Exception e) {
            log.error("Failed to process PaymentFailedEvent for Order ID: {}, Tracking ID: {}", event.getOrderId(), event.getTrackingId());
            throw e;
        }
    }
}
