package com.mq.example;

import com.mq.core.consumer.Consumer;
import com.mq.core.producer.Producer;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class App {

    private static Producer producer = new Producer();
    private static Consumer consumer = new Consumer();
    public static void main(String[] args) throws InterruptedException {
        startMQProducer();

        startMQConsumer();
    }

    private static void startMQConsumer() {
        Map<String, Object> properties = new HashMap<>(1);
        properties.put("url", "http://localhost:8080");
        properties.put("topic","testTopic");
        properties.put("group","groupTest");
        consumer.setProperties(properties);

        log.info("start consumer");
        List messages = consumer.poll();
        for (Object object : messages) {
            System.out.println(object.toString());
        }
    }

    private static void startMQProducer() {
        Map<String, Object> properties = new HashMap<>(1);
        properties.put("url", "http://localhost:8080");
        producer.setProperties(properties);

        log.info("start producer");
        String topic = "testTopic";
        for(int i = 0; i < 10; i++) {
            producer.send(topic, String.valueOf(i));
            log.info("send :" + i);
        }
    }

}
