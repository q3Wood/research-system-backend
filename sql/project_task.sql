-- ==========================================================
-- 表名：project_task (项目任务表)
-- 作用：项目负责人分配给各成员的具体任务清单（用于进度管理）。
-- 约束设计：
-- 1. 主码：id
-- 2. 外码：project_id 和 assignee_id
-- 3. 检查约束：status 限定在 0, 1, 2 之间
-- ==========================================================
-- 表名：project_task (项目任务表 - 企业级进阶版)
-- 作用：管理项目内的任务拆解、指派、截止日期与进度追踪。
-- ==========================================================
CREATE TABLE `project_task` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `project_id` BIGINT NOT NULL COMMENT '所属项目ID',
  `assigner_id` BIGINT NOT NULL COMMENT '任务派发人ID(通常是负责人)',
  `assignee_id` BIGINT NOT NULL COMMENT '被分配执行的成员ID',
  `task_name` VARCHAR(100) NOT NULL COMMENT '任务简短标题',
  `task_description` TEXT COMMENT '任务详细描述(要求、目标等)',
  `deadline` DATETIME DEFAULT NULL COMMENT '任务截止时间(非常重要)',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '任务状态: 0-待开始, 1-进行中, 2-已完成',
  `del_flag` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '派发时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_task_project` FOREIGN KEY (`project_id`) REFERENCES `project_info` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_task_assigner` FOREIGN KEY (`assigner_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_task_assignee` FOREIGN KEY (`assignee_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `chk_task_status` CHECK (`status` IN (0, 1, 2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目任务进度表';