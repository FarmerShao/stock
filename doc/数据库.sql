CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(50) DEFAULT '' COMMENT '角色名',
  `remark` varchar(255) DEFAULT '' COMMENT '角色描述',
  `permissions` varchar(1000) DEFAULT '' COMMENT '权限列表(英文逗号分隔）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;