package com.cyanide9102.paymentservice.payment.service.impl;

import com.cyanide9102.common.event.order.OrderPlacedEvent;
import com.cyanide9102.common.event.payment.PaymentCompletedEvent;
import com.cyanide9102.paymentservice.payment.Payment;
import com.cyanide9102.paymentservice.payment.PaymentRepository;
import com.cyanide9102.paymentservice.payment.PaymentStatus;
import com.cyanide9102.paymentservice.payment.service.PaymentService;
import com.cyanide9102.paymentservice.producer.PaymentEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventProducer paymentEventProducer;

    @Transactional
    @Override
    public void processOrderPayment(OrderPlacedEvent orderEvent) {

        Payment payment = Payment.builder().orderId(orderEvent.orderId()).userId(orderEvent.userId()).trackingId(orderEvent.trackingId()).amount(orderEvent.totalAmount()).status(PaymentStatus.COMPLETED).build();
        payment = paymentRepository.save(payment);

        PaymentCompletedEvent paymentEvent = new PaymentCompletedEvent(orderEvent.trackingId(), payment.getId(), orderEvent.orderId(), orderEvent.totalAmount(), orderEvent.paymentToken(), orderEvent.userId(), null);
        paymentEventProducer.publishPaymentCompletedEvent(paymentEvent);
    }
}
