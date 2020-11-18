package com.example.jdbc;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

public class HikarJDBC {
    public static void main(String[] args) throws Exception{
        HikarJDBC hikarJDBC = new HikarJDBC();
        hikarJDBC.connect();
    }

    private static final  Properties properties = new Properties();
    static {
        properties.setProperty("jdbcUrl", DatabaseConstant.JDBC_URL);
        properties.setProperty("driverClassName", DatabaseConstant.DRIVER_CLASS);
        properties.setProperty("dataSource.user", DatabaseConstant.USERNAME);
        properties.setProperty("dataSource.password", DatabaseConstant.PASSWORD);
        properties.setProperty("dataSource.maximumPoolSize", DatabaseConstant.MAX_POO_SIZE);
    }

    private void connect() throws Exception {
        HikariConfig hikariConfig = new HikariConfig(properties);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        Connection connection = hikariDataSource.getConnection();
        System.out.println("========正在初始化user表========");
        connection.prepareStatement(DatabaseConstant.INIT_SQL).execute();
        System.out.println("========正在新增user数据========");
        connection.prepareStatement(DatabaseConstant.INSERT_SQL).execute();
        //查询
        ResultSet resultSet = connection.prepareStatement(DatabaseConstant.QUERY_SQL).executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("name") +
                    ", " + resultSet.getString("sex"));
        }
        System.out.println("========正在修改user数据========");
        connection.prepareStatement(DatabaseConstant.UPDATE_SQL).execute();
        //查询
        resultSet = connection.prepareStatement(DatabaseConstant.QUERY_SQL).executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("name") +
                    ", " + resultSet.getString("sex"));
        }
        System.out.println("========正在删除user数据========");
        connection.prepareStatement(DatabaseConstant.DEL_SQL).execute();
        resultSet = connection.prepareStatement(DatabaseConstant.QUERY_SQL).executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("name") +
                    ", " + resultSet.getString("sex"));
        }
        //关闭连接
        connection.close();

    }
}