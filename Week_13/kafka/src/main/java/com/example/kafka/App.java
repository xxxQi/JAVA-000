package com.example.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class App  implements ApplicationRunner {
    private final String topic = "test32";
    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        float min = 0;
        float max = 100000;
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            threadPool.execute(()->{
                for (int j = 0; j < 10000; j++) {
                    kafkaTemplate.send(topic, new Order(
                            System.nanoTime(),
                            System.nanoTime(),
                            "USD",
                            new BigDecimal(Math.random() * (max - min) + min)
                                    .setScale(2, BigDecimal.ROUND_DOWN)));
                }
            });
        }
    }
}
