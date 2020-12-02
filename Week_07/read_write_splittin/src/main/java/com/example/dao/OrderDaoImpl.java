package com.example.dao;

import com.example.domain.Order;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    private final JdbcTemplate jdbcTemplate;

    private final String SELECT_SQL = "SELECT * FROM `order` LIMIT 1";
    private final String INSERT_SQL = "INSERT INTO `order`(`id`, `order_no`, `buyer_id`, `seller_id`, `amount`, `status`) VALUES (?,?,?,?,?,?)";

    @Autowired
    public OrderDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Order> findOne() {
        return jdbcTemplate.query(SELECT_SQL, (resultSet, i) -> {
            Order order = new Order();
            order.setId(resultSet.getLong(1));
            order.setOrderNo(resultSet.getLong(2));
            order.setBuyerId(resultSet.getLong(3));
            order.setSellerId(resultSet.getLong(4));
            order.setAmount(resultSet.getBigDecimal(5));
            return order;
        });
    }

    @Override
    public boolean insert() {
        long l = System.nanoTime();
        int update = jdbcTemplate.update(INSERT_SQL, ps -> {
            ps.setLong(1, l);
            ps.setLong(2, l);
            ps.setLong(3, 1);
            ps.setLong(4, 1);
            ps.setBigDecimal(5, new BigDecimal("0.01"));
            ps.setInt(6, 1);
        });
        System.out.println("insert update :" + update);
        return update > 0;
    }
}