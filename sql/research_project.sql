-- ==========================================================
-- 表名：research_project (科研项目表)
-- 作用：存储科研项目的核心生命周期数据。
-- 约束设计：
-- 1. 主码：id
-- 2. 外码 (Foreign Key)：leader_id 参照 sys_user(id)
-- 3. 检查约束：budget (预算金额不能为负数)
-- ==========================================================
-- ==========================================================
-- 表名：research_project (科研项目主表 - 终极版)
-- ==========================================================
CREATE TABLE `research_project` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '项目ID',
  `project_name` VARCHAR(100) NOT NULL COMMENT '项目名称',
  `description` TEXT COMMENT '项目简介',
  `budget` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '项目总预算(元) - 初始核定金额',
  `balance` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '可用余额(元) - 随报销动态递减',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态:0-申报待审,1-执行中,2-已结题,3-已驳回',
  `leader_id` BIGINT NOT NULL COMMENT '项目负责人ID',
  `del_flag` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志：0-正常，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '申报时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_project_leader` FOREIGN KEY (`leader_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `chk_project_budget` CHECK (`budget` >= 0),
  CONSTRAINT `chk_project_balance` CHECK (`balance` >= 0 AND `balance` <= `budget`) -- 绝杀约束：余额不能为负，也不能大于总预算
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='科研项目主表';