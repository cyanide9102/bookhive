package com.cyanide9102.paymentservice.producer;

import com.cyanide9102.common.event.payment.PaymentCompletedEvent;
import com.cyanide9102.paymentservice.config.KafkaConfig;
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

        log.info("Publishing PaymentCompletedEvent for Order ID: {}", event.orderId());
        kafkaTemplate.send(KafkaConfig.PAYMENT_COMPLETED_TOPIC, event.orderId(), event);
    }
}
