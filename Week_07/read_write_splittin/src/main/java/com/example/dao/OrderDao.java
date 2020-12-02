package com.example.dao;

import com.example.domain.Order;

import java.util.List;

/**
 * @author Reuben
 * @since 2020-12-02
 */
public interface OrderDao {

    List<Order> findOne();

    boolean insert();

}