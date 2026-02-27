-- ==========================================================
-- 表名：sys_user (系统用户表)
-- 作用：存储所有用户的登录信息、联系方式和系统角色。
-- 约束设计：
-- 1. 主码：id
-- 2. 唯一约束：username (保证学号/工号唯一)
-- 3. 检查约束：role 只能是 0(科研人员), 1(管理员), 2(超管)
-- ==========================================================
CREATE TABLE `sys_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` VARCHAR(50) NOT NULL COMMENT '登录账号(学号/工号)',
  `password` VARCHAR(100) NOT NULL COMMENT '登录密码(BCrypt加密)',
  `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '电子邮箱',
  `role` TINYINT NOT NULL DEFAULT 0 COMMENT '角色标识：0-科研人员，1-普通管理员，2-超级管理员',
  `del_flag` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标志：0-正常，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '账号创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  CONSTRAINT `chk_user_role` CHECK (`role` IN (0, 1, 2)),
  CONSTRAINT `chk_user_del` CHECK (`del_flag` IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 初始化超级管理员账号 (密码明文: 123456)
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `role`, `phone`, `email`, `del_flag`) 
VALUES ('admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '系统超级管理员', 2, '13800000000', 'admin@school.edu.cn', 0);