CREATE TABLE IF NOT EXISTS `account`(
	`id` bigint(20) PRIMARY KEY NOT NULL COMMENT '账号 ID',
	`username` varchar(32) NOT NULL UNIQUE COMMENT '账号',
	`password` varchar(64) NOT NULL COMMENT '密码',
	`salt` varchar(64) NOT NULL COMMENT '密码盐',
	`status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '账号状态(1-正常，2-禁用)',
	`last_login_time` timestamp COMMENT '最后一次登录',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT  '账号表';

CREATE TABLE IF NOT EXISTS `user`(
	`id` bigint(20) PRIMARY KEY  COMMENT '账号基本信息 ID',
	`account_id` bigint(20) NOT NULL COMMENT '账号 ID',
	`phone` varchar(32) NOT NULL DEFAULT '' COMMENT '手机号',
	`nick_name` varchar(20) NOT NULL COMMENT '昵称',
	`gender` tinyint(1) NOT NULL DEFAULT 0 COMMENT '性别(1-男，2-女)',
	`head_img_url` varchar(255) NOT NULL  COMMENT '头像',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '用户表';

CREATE TABLE IF NOT EXISTS `goods`(
	`id` bigint(20) PRIMARY KEY  COMMENT '商品 ID',
	`company_id` bigint(20) NOT NULL COMMENT '商家 ID',
	`category_id` int(11) NOT NULL COMMENT '商品分类 ID',
	`name` varchar(64) NOT NULL COMMENT '商品名称',
	`price` decimal(8,2) NOT NULL COMMENT '商品价格',
	`detail` varchar(255) NOT NULL COMMENT '商品详情',
	`status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '商品状态(1-上架,2-下架)',
	`del_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '删除状态(0-已删除,1-未删除)',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '商品表';

CREATE TABLE IF NOT EXISTS `goods_stock`(
	`id` bigint(20) PRIMARY KEY COMMENT '库存 ID',
	`goods_id` bigint(20) NOT NULL COMMENT '商品 ID',
	`count` int(10) NOT NULL COMMENT '商品数量',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '商品库存表';

CREATE TABLE IF NOT EXISTS `order`(
	`id` bigint(20) PRIMARY KEY COMMENT '订单 ID',
	`order_no` varchar(20) NOT NULL COMMENT '订单编号',
	`buyer_id` bigint(20) NOT NULL COMMENT '账号/买家 ID',
	`seller_id` bigint(20) NOT NULL COMMENT '账号/卖家 ID', 
	`amount` decimal(8,2) NOT NULL COMMENT '订单金额',
	`status`  int(2) NOT NULL COMMENT '订单状态',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '订单表';


 CREATE TABLE IF NOT EXISTS `order_list`(
 	`id` bigint(20) PRIMARY KEY COMMENT '订单项 ID',
 	`order_id` bigint(20) NOT NULL COMMENT '订单 ID',
 	`goods_id` bigint(20) NOT NULL COMMENT '商品 ID',
 	`quantity` int(4) NOT NULL COMMENT '数量'
 ) ENGINE = InnoDB
   DEFAULT CHARSET = utf8mb4 COMMENT '订单清单表';