package com.cyanide9102.common.kafka;

public final class KafkaTopics {

    public static final String ORDER_CREATED = "bookhive.order.created";
    public static final String ORDER_COMPLETED = "bookhive.order.completed";

    public static final String PAYMENT_COMPLETED = "bookhive.payment.completed";
    public static final String PAYMENT_FAILED = "bookhive.payment.failed";

    private KafkaTopics() {
    }
}
