package com.cyanide9102.paymentservice.payment.service.impl;

import com.cyanide9102.common.event.payment.PaymentCompletedEvent;
import com.cyanide9102.paymentservice.consumer.OrderCreatedCommand;
import com.cyanide9102.paymentservice.payment.Payment;
import com.cyanide9102.paymentservice.payment.PaymentRepository;
import com.cyanide9102.paymentservice.payment.PaymentStatus;
import com.cyanide9102.paymentservice.payment.service.PaymentService;
import com.cyanide9102.paymentservice.producer.PaymentEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventProducer paymentEventProducer;

    @Transactional
    @Override
    public void processOrderPayment(OrderCreatedCommand command) {

        Payment payment = Payment.builder().orderId(command.getOrderId()).userId(command.getUserId()).trackingId(command.getTrackingId()).amount(command.getTotalAmount()).status(PaymentStatus.COMPLETED).build();
        payment = paymentRepository.save(payment);

        PaymentCompletedEvent paymentEvent = PaymentCompletedEvent.builder().paymentId(payment.getId()).orderId(payment.getOrderId()).userId(payment.getUserId()).trackingId(payment.getTrackingId()).totalAmount(payment.getAmount()).build();
        paymentEventProducer.publishPaymentCompletedEvent(paymentEvent);
    }
}
