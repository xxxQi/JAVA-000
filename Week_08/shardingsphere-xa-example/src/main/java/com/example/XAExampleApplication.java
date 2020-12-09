package com.example;

import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author: Administrator
 * @description:
 */
public class XAExampleApplication {
    private static String configFile = "/META-INF/application.yaml";
    public static void main(String[] args) throws IOException, SQLException {
        testXA();
    }

    private static void testXA() throws IOException, SQLException {
        DataSource dataSource = getShardingDataSource();
        TransactionTypeHolder.set(TransactionType.XA);
        Connection conn = dataSource.getConnection();
        String sql = "INSERT INTO `t_order`( `order_no`, `buyer_id`, `seller_id`, `amount`, `status`, `create_time`, `update_time`) VALUES (UUID_SHORT(), 1, 2, 0.00, 0, '2020-11-29 23:19:46', '2020-11-29 23:19:46');";
        System.out.println(" XA insert begin ");
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (int i = 0; i < 10; i++) {
                statement.executeUpdate();
            }
            conn.commit();
        }
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (int i = 0; i < 10; i++) {
                statement.executeUpdate();
            }
            conn.commit();
        } catch (Exception e) {
            System.out.println(" XA insert failed");
            conn.rollback();
        } finally {
            conn.close();
        }
        System.out.println(" XA insert end");
    }


    private static DataSource getShardingDataSource() throws IOException, SQLException {
        return YamlShardingSphereDataSourceFactory.createDataSource(new File(Class.class.getResource(configFile).getFile()));
    }
}
