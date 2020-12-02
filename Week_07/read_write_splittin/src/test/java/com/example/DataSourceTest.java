package com.example;

import com.example.domain.Order;
import com.example.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class DataSourceTest {
    @Autowired
    private OrderService orderService;


    @Test
    void testDynamicDataSource() {
        int sum = 100;
        for (int i = 0; i < sum; i++) {
            Order order = orderService.findOne();
            log.info(" testDynamicDataSource:{}",order);
        }
    }

    @Test
    void testInsertOrder(){
        boolean success = orderService.insert();
        log.info("testInsertOrder:{}",success);
    }


}