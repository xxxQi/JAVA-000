package com.example.jdbc;

import java.sql.*;


public class PrimitivePreStatmentJDBC {

    public static void main(String[] args) throws Exception{
        PrimitivePreStatmentJDBC jdbc = new PrimitivePreStatmentJDBC();
        jdbc.connect();
    }

    private void connect() throws Exception{
        //加载H2数据库驱动
        Class.forName(DatabaseConstant.DRIVER_CLASS);
        // 根据连接URL，用户名，密码获取数据库连接
        Connection connection = DriverManager.getConnection(
                DatabaseConstant.JDBC_URL,
                DatabaseConstant.USERNAME,
                DatabaseConstant.PASSWORD);
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