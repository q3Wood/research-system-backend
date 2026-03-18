-- 切换到你的数据库
USE research_system;

-- 如果表存在则删除（注意备份数据）
DROP TABLE IF EXISTS `user`;

-- 创建科研系统用户表
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) NOT NULL COMMENT '登录账号 (如工号)',
  `password` varchar(255) NOT NULL COMMENT '登录密码 (加密存储)',
  `real_name` varchar(50) NOT NULL COMMENT '真实姓名',
  `role` tinyint NOT NULL DEFAULT '2' COMMENT '用户角色: 0-科研机构管理员, 1-项目负责人, 2-科研人员',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '账号状态: 0-正常, 1-禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

