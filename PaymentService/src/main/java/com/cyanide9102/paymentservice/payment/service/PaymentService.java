package com.cyanide9102.paymentservice.payment.service;

import com.cyanide9102.paymentservice.consumer.OrderCreatedCommand;

public interface PaymentService {

    void processOrderPayment(OrderCreatedCommand command);
}
