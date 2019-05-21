SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

#创建用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` VARCHAR(255) NOT NULL COMMENT '登录用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `email` VARCHAR(255) DEFAULT NULL COMMENT '邮件地址',
  `telephone` VARCHAR(255) DEFAULT NULL COMMENT '联系电话',
  `portrait` VARCHAR(512) DEFAULT NULL COMMENT '头像文件路径',
  `status` INT(2) DEFAULT 0 COMMENT '帐户状态: 0-待审核 1-正常 2-锁定',
  `regist_time` DATE DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci ROW_FORMAT = Compact;

#创建角色表
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role` VARCHAR(255) NOT NULL COMMENT '角色名称',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci ROW_FORMAT = Compact;

#创建权限表
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `permission` VARCHAR(255) NOT NULL COMMENT '权限路径',
  `type` INT(2) NOT NULL COMMENT '权限类型: 0-菜单，1-方法',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_general_ci ROW_FORMAT = Compact;


#设置用户角色关联表
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` INT(11) NOT NULL,
  `role_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_user_role_role_1` (`role_id`),
  KEY `fk_user_role_user_1` (`user_id`),
  CONSTRAINT `fk_user_role_role_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_role_user_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_general_ci ROW_FORMAT = Compact;

#设置用户角色关联表
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` INT(11) NOT NULL,
  `permission_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_role_permission_role_1` (`role_id`),
  KEY `fk_role_permission_permission_1` (`permission_id`),
  CONSTRAINT `fk_role_permission_role_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_role_permission_permission_1` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_general_ci ROW_FORMAT = Compact;


# 股票基础信息表
DROP TABLE IF EXISTS `stock_basic`;
CREATE TABLE `stock_basic` (
  `ts_code` VARCHAR(16) NOT NULL COMMENT '主键-编码',
  `symbol` VARCHAR(16) NOT NULL COMMENT '股票代码',
  `name` VARCHAR(32) NOT NULL COMMENT '股票名称',
  `enname` VARCHAR(128)  COMMENT '英文全称',
  `fullname` VARCHAR(64) NOT NULL COMMENT '股票全称',
  `area` VARCHAR(32) NOT NULL COMMENT '地区',
  `market` VARCHAR(32) NOT NULL COMMENT '市场类型 （主板/中小板/创业板）',
  `exchange` VARCHAR(8) NOT NULL COMMENT '交易所代码 ，SSE上交所 SZSE深交所 ，默认SSE',
   `industry` VARCHAR(32) NOT NULL COMMENT '行业',
  `list_status` VARCHAR(2) NOT NULL COMMENT '上市状态： L上市 D退市 P暂停上市',
  `list_date` VARCHAR(16) NOT NULL COMMENT '上市日期',
  `delist_date` VARCHAR(16) COMMENT '退市日期',
  `is_hs` VARCHAR(2) NOT NULL COMMENT '是否沪深港通标的，N否 H沪股通 S深股通',
  PRIMARY KEY (`ts_code`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_general_ci ROW_FORMAT = Compact;


#上市公司基础信息表
DROP TABLE IF EXISTS `stock_company`;
CREATE TABLE `stock_company` (
  `ts_code` VARCHAR(16) NOT NULL COMMENT '主键-编码',
  `exchange` VARCHAR(8) COMMENT '交易所代码 ，SSE上交所 SZSE深交所',
  `chairman` VARCHAR(64) COMMENT '法人代表',
  `manager` VARCHAR(32) COMMENT '总经理',
  `secretary` VARCHAR(32) COMMENT '董秘',
  `reg_capital` float COMMENT '注册资本',
  `setup_date` VARCHAR(16) COMMENT '注册日期',
  `province` VARCHAR(16) COMMENT '所在省份',
  `city` VARCHAR(16) COMMENT '所在城市',
  `introduction` VARCHAR(1024) COMMENT '公司介绍',
  `website` VARCHAR(64) COMMENT '公司主页',
  `email` VARCHAR(128) COMMENT '电子邮件',
  `office` VARCHAR(64) COMMENT '办公室',
  `employees` int(6) COMMENT '员工人数',
  `main_business` VARCHAR(2048) COMMENT '主要业务及产品',
  `business_scope` VARCHAR(2048) COMMENT '经营范围',
  PRIMARY KEY (`ts_code`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_general_ci ROW_FORMAT = Compact;

# 日线行情表
DROP TABLE IF EXISTS `stock_daily`;
CREATE TABLE `stock_daily` (
  `ts_code` VARCHAR(16) NOT NULL COMMENT '主键-编码',
  `trade_date` VARCHAR(16) COMMENT '交易日期',
  `open` float(8,2) COMMENT '开盘价',
  `high` float(8,2) COMMENT '最高价',
  `low` float(8,2) COMMENT '最低价',
  `close` float(8,2) COMMENT '收盘价',
  `pre_close` float(8,2) COMMENT '昨收价',
  `change` float(8,2) COMMENT '涨跌额',
  `pct_chg` float(8,2) COMMENT '涨跌幅 （未复权）',
  `vol` float(16,2) COMMENT '成交量 （手）',
  `amount` float(18,2) COMMENT '成交额 （千元）',
  PRIMARY KEY (`ts_code`, `trade_date`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_general_ci ROW_FORMAT = Compact;


# 个股周线行情表
DROP TABLE IF EXISTS `stock_weekly`;
CREATE TABLE `stock_weekly` (
  `ts_code` VARCHAR(16) NOT NULL COMMENT '主键-编码',
  `trade_date` VARCHAR(16) COMMENT '交易日期（每周五日期，YYYYMMDD格式）',
  `open` float(8,2) COMMENT '周开盘价',
  `high` float(8,2) COMMENT '周最高价',
  `low` float(8,2) COMMENT '周最低价',
  `close` float(8,2) COMMENT '周收盘价',
  `pre_close` float(8,2) COMMENT '上一周收盘价',
  `change` float(8,2) COMMENT '周涨跌额',
  `pct_chg` float(8,2) COMMENT '周涨跌幅 （未复权）',
  `vol` float(16,2) COMMENT '周成交量 （手）',
  `amount` float(18,2) COMMENT '周成交额 （千元）',
  PRIMARY KEY (`ts_code`, `trade_date`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_general_ci ROW_FORMAT = Compact;

# 个股月线行情表
DROP TABLE IF EXISTS `stock_monthly`;
CREATE TABLE `stock_monthly` (
  `ts_code` VARCHAR(16) NOT NULL COMMENT '主键-编码',
  `trade_date` VARCHAR(16) COMMENT '交易日期（每月最后一个交易日日期，YYYYMMDD格式）',
  `open` float(8,2) COMMENT '月开盘价',
  `high` float(8,2) COMMENT '月最高价',
  `low` float(8,2) COMMENT '月最低价',
  `close` float(8,2) COMMENT '月收盘价',
  `pre_close` float(8,2) COMMENT '上一月收盘价',
  `change` float(8,2) COMMENT '月涨跌额',
  `pct_chg` float(8,2) COMMENT '月涨跌幅 （未复权）',
  `vol` float(16,2) COMMENT '月成交量 （手）',
  `amount` float(18,2) COMMENT '月成交额 （千元）',
  PRIMARY KEY (`ts_code`, `trade_date`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_general_ci ROW_FORMAT = Compact;

# 选中股票信息表
DROP TABLE IF EXISTS `stock_selected`;
CREATE TABLE `stock_selected` (
  `ts_code` VARCHAR(16) NOT NULL COMMENT '主键-编码',
  `select_date` VARCHAR(16) COMMENT '选中日期',
  `select_price` float(8,2) COMMENT '选中时价格',
  `highest_date` VARCHAR(16) COMMENT '最高价日期',
  `highest_price` float(8,2) COMMENT '选中后最高价',
  `current_date` VARCHAR(16) COMMENT '当前日期',
  `current_price` float(8,2) COMMENT '当前价格',
  `current_change` float(8,2) COMMENT '当前涨跌额',
  `recommender` VARCHAR(32) COMMENT '推荐人',  
  `recommend_level` int(2) COMMENT '推荐星级（1-5）',
  `recommend_reason` VARCHAR(1024) COMMENT '推荐原因',  
  PRIMARY KEY (`ts_code`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_general_ci ROW_FORMAT = Compact;

# 指数基本信息表
DROP TABLE IF EXISTS `index_basic`;
CREATE TABLE `index_basic` (
  `ts_code` VARCHAR(16) NOT NULL COMMENT '主键-指数代码',
  `name` VARCHAR(32) COMMENT '指数简称',
  `fullname` VARCHAR(64) COMMENT '指数全称',
  `market` VARCHAR(8) COMMENT '市场代码：MSCI，CSI(中证指数)，SSE(上交所指数)，SZSE(深交所指数)，CICC(中金所指数)，SW(申万指数)，OTH(其他指数)',
  `publisher` VARCHAR(64) COMMENT '发布方',
  `index_type` VARCHAR(32) COMMENT '指数风格',
  `category` VARCHAR(64) COMMENT '指数类别',
  `base_date` VARCHAR(16) COMMENT '基期',
  `base_point` float(8,2) COMMENT '基点',
  `list_date` VARCHAR(16) COMMENT '发布日期',  
  `exp_date` VARCHAR(16) COMMENT '终止日期', 
  `weight_rule` VARCHAR(16) COMMENT '加权方式',
  `desc` VARCHAR(1024) COMMENT '描述',  
  PRIMARY KEY (`ts_code`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_general_ci ROW_FORMAT = Compact;


# 指数日线行情表
DROP TABLE IF EXISTS `index_daily`;
CREATE TABLE `index_daily` (
  `ts_code` VARCHAR(16) NOT NULL COMMENT 'TS指数代码',
  `trade_date` VARCHAR(16) COMMENT '交易日期',
  `open` float(8,2) COMMENT '开盘点位',
  `high` float(8,2) COMMENT '最高点位',
  `low` float(8,2) COMMENT '最低点位',
  `close` float(8,2) COMMENT '收盘点位',
  `pre_close` float(8,2) COMMENT '昨日收盘点',
  `change` float(8,2) COMMENT '涨跌点',
  `pct_chg` float(8,2) COMMENT '涨跌幅（%）',
  `vol` double(20,2) COMMENT '成交量（手）',
  `amount` double(20,2) COMMENT '成交额（千元）',
  PRIMARY KEY (`ts_code`, `trade_date`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_general_ci ROW_FORMAT = Compact;


# 指数周线行情表
DROP TABLE IF EXISTS `index_weekly`;
CREATE TABLE `index_weekly` (
  `ts_code` VARCHAR(16) NOT NULL COMMENT 'TS指数代码',
  `trade_date` VARCHAR(16) COMMENT '交易日期',
  `open` float(8,2) COMMENT '开盘点位',
  `high` float(8,2) COMMENT '最高点位',
  `low` float(8,2) COMMENT '最低点位',
  `close` float(8,2) COMMENT '收盘点位',
  `pre_close` float(8,2) COMMENT '上周收盘点',
  `change` float(8,2) COMMENT '涨跌点',
  `pct_chg` float(8,2) COMMENT '涨跌幅（%）',
  `vol` double(20,2) COMMENT '成交量（手）',
  `amount` double(20,2) COMMENT '成交额（千元）',
  PRIMARY KEY (`ts_code`, `trade_date`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_general_ci ROW_FORMAT = Compact;

# 指数月线行情表
DROP TABLE IF EXISTS `index_monthly`;
CREATE TABLE `index_monthly` (
  `ts_code` VARCHAR(16) NOT NULL COMMENT 'TS指数代码',
  `trade_date` VARCHAR(16) COMMENT '交易日期',
  `open` float(8,2) COMMENT '开盘点位',
  `high` float(8,2) COMMENT '最高点位',
  `low` float(8,2) COMMENT '最低点位',
  `close` float(8,2) COMMENT '收盘点位',
  `pre_close` float(8,2) COMMENT '上月收盘点',
  `change` float(8,2) COMMENT '涨跌点',
  `pct_chg` float(8,2) COMMENT '涨跌幅（%）',
  `vol` double(20,2) COMMENT '成交量（手）',
  `amount` double(20,2) COMMENT '成交额（千元）',
  PRIMARY KEY (`ts_code`, `trade_date`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COLLATE=utf8_general_ci ROW_FORMAT = Compact;

#设置检查外键
SET FOREIGN_KEY_CHECKS = 1;