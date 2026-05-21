package com.cyanide9102.paymentservice.payment.service;

import com.cyanide9102.common.event.order.OrderCreatedEvent;

public interface PaymentService {

    void processOrderPayment(ProcessPaymentCommand command);
}
