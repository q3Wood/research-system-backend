-- ==========================================================
-- 表名：project_member (项目成员关联表)
-- 作用：解决项目与科研人员之间的多对多(M:N)实体关系。
-- 约束设计：
-- 1. 主码：复合主码 (project_id, user_id)，防止同一人重复加入同一项目
-- 2. 外码 1：project_id 参照 research_project(id) (级联删除)
-- 3. 外码 2：user_id 参照 sys_user(id)
-- ==========================================================
-- ==========================================================
-- 表名：project_member (项目团队成员表 - 企业实战版)
-- 作用：管理项目参与人员，保留退出记录，适配现代 ORM 框架。
-- ==========================================================
CREATE TABLE `project_member` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '独立主键ID',
  `project_id` BIGINT NOT NULL COMMENT '项目ID',
  `user_id` BIGINT NOT NULL COMMENT '成员ID',
  `member_role` VARCHAR(50) DEFAULT '普通成员' COMMENT '项目内角色(如: 核心骨干, 科研助理)',
  `del_flag` TINYINT NOT NULL DEFAULT 0 COMMENT '状态标识：0-在组，1-已退出(软删除)',
  `join_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '加入团队时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_project_user` (`project_id`, `user_id`), -- 绝杀约束：保证同一个人不能重复加入同一个项目
  CONSTRAINT `fk_member_project` FOREIGN KEY (`project_id`) REFERENCES `project_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_member_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目团队成员表';