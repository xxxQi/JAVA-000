#学习笔记
1.（必做）设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。

- 下载shardingsphere-proxy根据本地拆分2个库，分别以geektime_geektime_${0..1}
- 修改/config下的server.xml和config.sharding.yml
- 启动/bin/start.bat脚本,登录proxy的mysql数据库，默认监控端口3307，账号，密码root
- 根据config.sharding.yml配置文件进行数据分片,创建16张t_order表，完成增删改查实例

server.xml
```
    authentication:
      users:
        root:
          password: root
        sharding:
          password: sharding 
          authorizedSchemas: sharding_db
    
    props:
      max-connections-size-per-query: 1
      acceptor-size: 16  # The default value is available processors count * 2.
      executor-size: 16  # Infinite by default.
      proxy-frontend-flush-threshold: 128  
      proxy-transaction-type: LOCAL
      proxy-opentracing-enabled: false
      proxy-hint-enabled: false
      query-with-cipher-column: true
      sql-show: false
      check-table-metadata-enabled: false

```

config.sharding.yml
```
schemaName: sharding_db

dataSourceCommon:
  username: root
  password: 123456
  connectionTimeoutMilliseconds: 30000
  idleTimeoutMilliseconds: 60000
  maxLifetimeMilliseconds: 1800000
  maxPoolSize: 50
  minPoolSize: 1
  maintenanceIntervalMilliseconds: 30000

dataSources:
  geektime_0:
    url: jdbc:mysql://127.0.0.1:3306/geektime_1?serverTimezone=UTC&useSSL=false
  geektime_1:
    url: jdbc:mysql://127.0.0.1:3306/geektime_2?serverTimezone=UTC&useSSL=false

rules:
- !SHARDING
  tables:
    t_order:
      actualDataNodes: geektime_${0..1}.t_order_${0..16}
      tableStrategy:
        standard:
          shardingColumn: id
          shardingAlgorithmName: t_order_inline
      keyGenerateStrategy:
        column: id
        keyGeneratorName: snowflake
  bindingTables:
    - t_order
  defaultDatabaseStrategy:
    standard:
      shardingColumn: id
      shardingAlgorithmName: database_inline
  defaultTableStrategy:
    none:
  
  shardingAlgorithms:
    database_inline:
      type: INLINE
      props:
        algorithm-expression: geektime_${id % 2}
    t_order_inline:
      type: INLINE
      props:
        algorithm-expression: t_order_${id % 2}
  
  keyGenerators:
    snowflake:
      type: SNOWFLAKE
      props:
        worker-id: 123

```
db.sql
```
#创建16张t_order表
CREATE TABLE IF NOT EXISTS `t_order` (
  `id` bigint(20) NOT NULL COMMENT '订单 ID',
  `order_no` varchar(20) NOT NULL COMMENT '订单编号',
  `buyer_id` bigint(20) NOT NULL COMMENT '账号/买家 ID',
  `seller_id` bigint(20) NOT NULL COMMENT '账号/卖家 ID',
  `amount` decimal(8,2) NOT NULL COMMENT '订单金额',
  `status` int(2) NOT NULL COMMENT '订单状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';
... 

INSERT INTO `t_order`( `order_no`, `buyer_id`, `seller_id`, `amount`, `status`, `create_time`, `update_time`) VALUES (UUID_SHORT(), 1, 2, 0.00, 0, '2020-11-29 23:19:46', '2020-11-29 23:19:46');
SELECT * FROM `t_order`;
```

2.（必做）基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布式事务应用 demo（二选一），提交到 Github。

