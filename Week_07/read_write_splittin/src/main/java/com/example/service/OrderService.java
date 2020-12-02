package com.example.service;

import com.example.annotation.DataSource;
import com.example.dao.OrderDao;
import com.example.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDAO;

    @DataSource(loadBalance = true)
    public Order findOne(){
        List<Order> orderList = orderDAO.findOne();
        return !CollectionUtils.isEmpty(orderList) ? orderList.get(0) : null;
    }

    @DataSource
    public boolean insert(){
        return orderDAO.insert();
    }
}