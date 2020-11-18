package com.example.jdbc;

import java.sql.*;


public class PrimitiveJDBC {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        PrimitiveJDBC jdbc = new PrimitiveJDBC();
        jdbc.connect();
    }

    private void connect() throws ClassNotFoundException, SQLException {
        //加载H2数据库驱动
        Class.forName(DatabaseConstant.DRIVER_CLASS);
        // 根据连接URL，用户名，密码获取数据库连接
        Connection connection = DriverManager.getConnection(
                DatabaseConstant.JDBC_URL,
                DatabaseConstant.USERNAME,
                DatabaseConstant.PASSWORD);
        Statement stmt = connection.createStatement();
        System.out.println("========正在初始化user表========");
        stmt.execute(DatabaseConstant.INIT_SQL);
        System.out.println("========正在新增user数据========");
        stmt.executeUpdate(DatabaseConstant.INSERT_SQL);
        //查询
        ResultSet resultSet = stmt.executeQuery(DatabaseConstant.QUERY_SQL);
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("name") +
                    ", " + resultSet.getString("sex"));
        }
        System.out.println("========正在修改user数据========");
        stmt.executeUpdate(DatabaseConstant.UPDATE_SQL);
        //查询
        resultSet = stmt.executeQuery(DatabaseConstant.QUERY_SQL);
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("name") +
                    ", " + resultSet.getString("sex"));
        }
        System.out.println("========正在删除user数据========");
        stmt.executeUpdate(DatabaseConstant.DEL_SQL);
        resultSet = stmt.executeQuery(DatabaseConstant.QUERY_SQL);
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("name") +
                    ", " + resultSet.getString("sex"));
        }
        //释放资源
        stmt.close();
        //关闭连接
        connection.close();
    }
}