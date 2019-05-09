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

CREATE TABLE `stock_user` (
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

CREATE TABLE `stock_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '姓名',
  `mobile` varchar(100) NOT NULL DEFAULT '' COMMENT '手机号',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：1 启用 2 禁用',
  `count` int(11) NOT NULL DEFAULT 0 COMMENT '客户数 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '经纪人' ;

CREATE TABLE `stock_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trade_server_ip` varchar(50) NOT NULL COMMENT '交易服务器IP',
  `trade_port` int(10) DEFAULT NULL COMMENT '端口号',
  `stock_jobber_id` int(10) DEFAULT NULL COMMENT '券商ID',
  `stock_exchange_id` int(10) DEFAULT NULL COMMENT '营业部ID',
  `account_type` int(10) DEFAULT NULL COMMENT '账号类型',
  `account_no` varchar(50) DEFAULT NULL COMMENT '登录账号',
  `bankroll_account` varchar(50) DEFAULT NULL COMMENT '资金账号',
  `account_password` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `communication_password` varchar(50) DEFAULT NULL COMMENT '通讯密码',
  `financing` int(1) DEFAULT NULL COMMENT '是否为融券账号 1 是 2 不是',
  `delete_flag` int(1) DEFAULT '0' COMMENT '0 未删除 1 删除',
  `is_able` int(1) DEFAULT NULL COMMENT '0 可用 1不可用',
  `qs_flag` int(1) DEFAULT NULL COMMENT '券商编号',
  `amount_limit` decimal(14,2) DEFAULT '0.00' COMMENT '单支股票限额',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `collateral` int(1) DEFAULT '2' COMMENT '是否允许担保品建仓 1 允许 2 不允许',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT '股票账户' ;

CREATE TABLE `stock_risk_management` (
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT '风控配置表' ;

CREATE TABLE `stock_user_bank` (
  `id` bigint(11) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '主键',
  `card_no` varchar(100) NOT NULL COMMENT '银行卡号',
  `id_Card` varchar(100) NOT NULL COMMENT '身份证号',
  `uname` varchar(50) NOT NULL COMMENT '姓名',
  `phone` varchar(100) NOT NULL COMMENT '手机号',
  `user_id` int(20) NOT NULL,
  `no_agree` varchar(30) DEFAULT NULL COMMENT '协议号',
  `bank_name` varchar(30) NOT NULL COMMENT '银行名称',
  `creator` varchar(255) DEFAULT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `checkedstate` varchar(1) DEFAULT NULL COMMENT '选中状态 0.未选中 1.已选中',
  `isdelete` varchar(1) DEFAULT NULL COMMENT '是否删除：0否，1是',
  `province` varchar(50) DEFAULT NULL COMMENT '省份',
  `city` varchar(50) DEFAULT NULL COMMENT '城市',
  `bank_branch` varchar(50) DEFAULT NULL COMMENT '支行名称',
  `bank_saved_mobile` varchar(100) DEFAULT NULL COMMENT '银行预留手机号',
  PRIMARY KEY (`id`),
  KEY `idx_card_no` (`card_no`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2945 DEFAULT CHARSET=utf8 COMMENT '用户银行卡绑定表' ;


