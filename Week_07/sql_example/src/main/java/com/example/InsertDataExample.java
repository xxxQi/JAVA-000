package com.example;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *      订单量：1,插入耗时333 ms
 *      订单量：10,插入耗时355 ms
 *      订单量：100,插入耗时421 ms
 *      订单量：1000,插入耗时483 ms
 *      订单量：10000,插入耗时1346 ms
 *      订单量：100000,插入耗时7117 ms
 *      订单量：1000000,插入耗时67189 ms
 *      订单量：10000000 OutOfMemoryError: GC overhead limit exceeded
 */
public class InsertDataExample {
    private static final String URL = "jdbc:mysql://localhost:3306/geektime?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        InsertDataExample millionDataExample = new InsertDataExample();
        int sum = 10000000;
        long startTime = System.currentTimeMillis();
        millionDataExample.insert(sum);
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("订单量：%s,插入耗时%s ms",sum, endTime - startTime));
    }

    private void insert(int sum) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            Class.forName(DRIVER_CLASS);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            String sql = "INSERT INTO `geektime`.`order`(`id`, `order_no`, `buyer_id`, `seller_id`, `amount`, `status`) VALUES (?,?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < sum; i++) {
                long l = System.nanoTime();
                ps.setLong(1, l);
                ps.setLong(2, l);
                ps.setLong(3, 1);
                ps.setLong(4, 1);
                ps.setBigDecimal(5, new BigDecimal("0.01"));
                ps.setInt(6, 1);
                ps.addBatch();
            }
            ps.executeBatch();
            connection.commit();
        } finally{
            assert ps != null;
            ps.close();
            connection.close();
        }
    }
}