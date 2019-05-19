CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '角色描述',
  `permissions` varchar(1000) NOT NULL DEFAULT '' COMMENT '权限列表(英文逗号分隔）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户修改时间',
  `role_id` int(11) NOT NULL DEFAULT 0 COMMENT '角色ID',
  `user_name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名称',
  `user_pwd` varchar(20) NOT NULL DEFAULT '' COMMENT '用户密码',
  `last_login_ip` varchar(50) NOT NULL DEFAULT '' COMMENT '最后登录IP',
  `last_login_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `role_type` tinyint(2) NOT NULL DEFAULT 1 COMMENT '角色类型 ：1.系统管理员',
  `disable_flag` tinyint(2) NOT NULL DEFAULT 2 COMMENT '1.删除 2.未删除',
  `login_key` varchar(20) NOT NULL DEFAULT '',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(100) NOT NULL DEFAULT'' COMMENT '手机号码',
  `disable_flag` smallint(2)  NOT NULL DEFAULT 1 COMMENT '1 启用 2 黑名单',
  `nickname` varchar(50)  NOT NULL DEFAULT '' COMMENT '昵称',
  `password` varchar(100)  NOT NULL DEFAULT '' COMMENT '密码',
  `total_out_money` decimal(12,2)  NOT NULL DEFAULT '0.00' COMMENT '总出金',
  `total_in_money` decimal(12,2)  NOT NULL DEFAULT '0.00' COMMENT '总入金',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '账户余额',
  `id_card` varchar(100)  NOT NULL DEFAULT '' COMMENT '身份证',
  `truth_name` varchar(100)  NOT NULL DEFAULT '' COMMENT '姓名',
  `broker` int(11)  NOT NULL DEFAULT 1 COMMENT '经纪人Id',
  `broker_name` varchar(50)  NOT NULL DEFAULT '' COMMENT '经纪人姓名',
  `created_at` datetime  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `last_login_time` datetime  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户表' ;

CREATE TABLE `manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '姓名',
  `mobile` varchar(100) NOT NULL DEFAULT '' COMMENT '手机号',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：1 启用 2 禁用',
  `count` int(11) NOT NULL DEFAULT 0 COMMENT '客户数 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '经纪人' ;

CREATE TABLE `risk_management` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一识别',
  `min_lr` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最小杠杆比例',
  `max_lr` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最大杠杆比例',
  `default_stop_pr` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '默认止盈比例',
  `max_stop_pr` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最大止盈比例',
  `min_margin` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最小保证金金额',
  `max_loss_mr` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '保证金最大亏损比例',
  `keeping_mr` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '维持保证金担保比例',
  `strategy_max_point` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '单个策略最大点买金额',
  `user_max_point` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '个人最大点买金额',
  `user_max_buy` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '个人持仓总市值最大限额',
  `user_max_lose` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '允许最大跌幅',
  `user_max_increase` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '允许最大涨幅',
  `user_max_hold` decimal(14,2) NOT NULL DEFAULT '0.00' COMMENT '个人单支股票最大持仓市值',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '风控配置表' ;

CREATE TABLE `entrust_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `stock_code` varchar(10) NOT NULL DEFAULT '' COMMENT '股票代码',
  `stock_name` varchar(20) NOT NULL DEFAULT '' COMMENT '股票',
  `lots` int(11) NOT NULL DEFAULT '0' COMMENT '股数',
  `deal_lots` int(11) NOT NULL DEFAULT '0' COMMENT '已成交股数',
  `price` decimal(10,4) NOT NULL DEFAULT '0.00' COMMENT '委托价',
  `buy_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '委托单类型：1.限价单 2.市价单',
  `direction` tinyint(4) NOT NULL DEFAULT '0' COMMENT '委托单方向：1.买 2.卖',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '订单状态：1.未成交 2. 已成交 3.部分成交 4.用户撤销 5.待提交',
  `is_faker` tinyint(4) NOT NULL DEFAULT '2' COMMENT '是否是虚拟盘订单：1.是 2.否',
  `margin_rate` tinyint(4) NOT NULL DEFAULT '8' COMMENT '保证金倍数:卖单时为0',
  `margin` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '试算保证金：买单存在，卖单为0',
  `service_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '综合服务费',
  `delay_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '过期递延费',
  `hold_order_id` int(11) NOT NULL DEFAULT '0' COMMENT '系统平仓时指定持仓单ID',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
  `broker` int(11)  NOT NULL DEFAULT '1' COMMENT '经纪人Id',
  `created_date` date NOT NULL COMMENT '订单创建日期',
  `created_time` time NOT NULL COMMENT '订单创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单最后修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_us_sta_ty` (`user_id`,`status`,`type`) USING BTREE,
  KEY `idx_cd` (`created_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='委托单';

CREATE TABLE `holding_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `entrust_order_id` int(11) NOT NULL DEFAULT '0' COMMENT '委托单ID',
  `stock_code` varchar(10) NOT NULL DEFAULT '' COMMENT '股票代码',
  `stock_name` varchar(20) NOT NULL DEFAULT '' COMMENT '股票',
  `lots` int(11) NOT NULL DEFAULT '0' COMMENT '股数',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '成交价',
  `profit_price` decimal(10,4) NOT NULL DEFAULT '0.00' COMMENT '止盈价',
  `loss_price` decimal(10,4) NOT NULL DEFAULT '0.00' COMMENT '止损价',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '订单状态：1.持仓 2.平仓 3.平仓委托中',
  `is_unbind` tinyint(4) NOT NULL DEFAULT '2' COMMENT '是否与实盘脱钩：1.是 2.否',
  `is_believed` tinyint(4) NOT NULL DEFAULT '2' COMMENT '是否被信任（被信任的订单不会被系统强平）：1.是 2.否',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `profit` decimal(10,4) NOT NULL DEFAULT '0.00' COMMENT '盈利',
  `margin_rate` tinyint(4) NOT NULL DEFAULT '8' COMMENT '保证金倍数',
  `margin` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '保证金',
  `delay_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '过期递延费',
  `valid_day` int(11) NOT NULL DEFAULT '2' COMMENT '持仓有效天数，为0时会被系统平仓',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
  `broker` int(11)  NOT NULL DEFAULT '1' COMMENT '经纪人Id',
  `created_date` date NOT NULL COMMENT '订单创建日期',
  `created_time` time NOT NULL COMMENT '订单创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单最后修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_sta_uid_ty` (`status`,`user_id`,`stock_name`) USING BTREE,
  KEY `idx_cd` (`created_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='持仓单';

CREATE TABLE `fund_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `direction` smallint NOT NULL DEFAULT '1' COMMENT '资金流水方向：1.转入 2.转出',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '流水类型',
  `type_desc` varchar(20) NOT NULL DEFAULT '' COMMENT '流水原因说明',
  `fund_amount` decimal(10,4) NOT NULL DEFAULT '0' COMMENT '资金额度',
  `result_amount` decimal(10,4) NOT NULL DEFAULT '0' COMMENT '变化后的资金总额',
  `broker` int(11)  NOT NULL DEFAULT '1' COMMENT '经纪人Id',
  `created_date` date NOT NULL COMMENT '订单创建日期',
  `created_time` time NOT NULL COMMENT '订单创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_uid_dir_tp` (`user_id`,`direction`,`type`) USING BTREE,
  KEY `idx_cd` (`created_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资金账单';


