package com.cyanide9102.paymentservice.config;

import com.cyanide9102.common.kafka.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic paymentCompletedTopic() {

        return TopicBuilder.name(KafkaTopics.PAYMENT_COMPLETED).partitions(1).replicas(1).build();
    }
}
