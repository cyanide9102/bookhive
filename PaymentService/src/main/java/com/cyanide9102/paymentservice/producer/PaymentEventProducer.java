package com.cyanide9102.paymentservice.producer;

import com.cyanide9102.common.event.payment.PaymentCompletedEvent;
import com.cyanide9102.common.event.payment.PaymentFailedEvent;
import com.cyanide9102.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishPaymentCompletedEvent(PaymentCompletedEvent event) {

        log.info("Publishing PaymentCompletedEvent for Order ID: {}, Tracking ID: {}", event.getOrderId(), event.getTrackingId());
        kafkaTemplate.send(KafkaTopics.PAYMENT_COMPLETED, event.getTrackingId(), event);
    }

    public void publishPaymentFailedEvent(PaymentFailedEvent event) {

        log.info("Publishing PaymentFailedEvent for Order ID: {}, Tracking ID: {}", event.getOrderId(), event.getTrackingId());
        kafkaTemplate.send(KafkaTopics.PAYMENT_FAILED, event.getTrackingId(), event);
    }
}
