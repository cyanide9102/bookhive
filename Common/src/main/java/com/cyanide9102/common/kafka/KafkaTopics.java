package com.cyanide9102.common.kafka;

public final class KafkaTopics {

    public static final String ORDER_CREATED = "order-created";
    public static final String ORDER_COMPLETED = "order-completed";
    public static final String ORDER_CANCELLED = "order-cancelled";

    public static final String PAYMENT_COMPLETED = "payment-completed";

    private KafkaTopics() {
    }
}
