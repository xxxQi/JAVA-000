package com.example.jdbc;


public interface DatabaseConstant {
    String DRIVER_CLASS = "org.h2.Driver";
    /**
     *内存模式
     */
//    String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    /**
     * TCP模式
     */
//    String JDBC_URL = "jdbc:h2:tcp://localhost/~/test";
    /**
     * 嵌入式模式
     */
    String JDBC_URL = "jdbc:h2:file:~/.h2/test;AUTO_SERVER=TRUE";
    String USERNAME = "sa";
    String PASSWORD = "";
    String MAX_POO_SIZE = "100";

    /**
     * 初始化
     */
    String INIT_SQL = "drop table if exists user;" +
            "create table user(id int primary key,name varchar(100),sex varchar(2));";
    String INSERT_SQL = "insert into user values(1, '小红', '男'),(2, '小黑', '女'),(3, '小白', '男'),(4, '小兰', '女')";
    String QUERY_SQL = "select * from user";
    String UPDATE_SQL = "update user set name = '小蓝' where id = 4";
    String DEL_SQL = "delete from user where id = 4";
}