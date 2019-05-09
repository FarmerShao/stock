CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(50) DEFAULT '' COMMENT '角色名',
  `remark` varchar(255) DEFAULT '' COMMENT '角色描述',
  `permissions` varchar(1000) DEFAULT '' COMMENT '权限列表(英文逗号分隔）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sys_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '用户修改时间',
  `role_id` int(11) DEFAULT 0 COMMENT '角色ID',
  `user_name` varchar(20) DEFAULT '' COMMENT '用户名称',
  `user_pwd` varchar(20) DEFAULT '' COMMENT '用户密码',
  `last_login_ip` varchar(50) DEFAULT '' COMMENT '最后登录IP',
  `last_login_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `role_type` tinyint(2) DEFAULT 1 COMMENT '角色类型 ：1.系统管理员',
  `disable_flag` tinyint(2) DEFAULT 2 COMMENT '1.删除 2.未删除',
  `login_key` varchar(20) DEFAULT '',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `stock_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(100) NOT '' COMMENT '手机号码',
  `disable_flag` smallint(2) DEFAULT '1' COMMENT '1 启用 2 黑名单',
  `nickname` varchar(50) DEFAULT '' COMMENT '昵称',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `total_out_money` decimal(12,2) DEFAULT '0.00' COMMENT '总出金',
  `total_in_money` decimal(12,2) DEFAULT '0.00' COMMENT '总入金',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '账户余额',
  `id_card` varchar(100) DEFAULT '' COMMENT '身份证',
  `truth_name` varchar(100) DEFAULT '' COMMENT '姓名',
  `broker` int(11) DEFAULT NULL COMMENT '经纪人Id',
  `broker_name` varchar(50) DEFAULT NULL COMMENT '经纪人姓名',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `last_login_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间',
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户表' ;

CREATE TABLE `strategy_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT '' COMMENT '姓名',
  `mobile` varchar(100) DEFAULT '' COMMENT '手机号',
  `status` tinyint(4) DEFAULT 1 COMMENT '状态：1 启用 2 禁用',
  `count` int(11) DEFAULT 0 COMMENT '客户数 ',
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '经纪人' ;

