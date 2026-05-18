package com.cyanide9102.paymentservice.payment.service;

import com.cyanide9102.common.event.order.OrderPlacedEvent;

public interface PaymentService {

    void processOrderPayment(OrderPlacedEvent orderEvent);
}
