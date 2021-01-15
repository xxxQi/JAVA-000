package com.example.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(id = "center-topic", topics = "test32")
    public void listen(Order order) {
        logger.info("Consumer receive [{}]", order);
    }

    @Bean
    public NewTopic topic() {
        return new NewTopic("test32", 3, (short) 2);
    }
}
