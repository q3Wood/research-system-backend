-- ==========================================================
-- 表名：project_fund_record (经费报销记录表)
-- 作用：记录科研人员的经费使用申请及管理员/负责人的审批状态。
-- 约束设计：
-- 1. 主码：id
-- 2. 外码：project_id 和 applicant_id
-- 3. 检查约束：amount 必须大于 0 (不能报销负数金额)
-- ==========================================================
-- ==========================================================
-- 表名：project_fund_record (经费报销申请表 - 极简流转版)
-- 作用：记录经费申请与状态。审批留痕统一交由 project_audit_log 记录。
-- ==========================================================
CREATE TABLE `project_fund_record` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '流水ID',
  `project_id` BIGINT NOT NULL COMMENT '所属项目ID',
  `applicant_id` BIGINT NOT NULL COMMENT '申请人ID(谁要花这笔钱)',
  `amount` DECIMAL(10,2) NOT NULL COMMENT '申请金额(元)',
  `usage_desc` VARCHAR(255) NOT NULL COMMENT '资金用途详细说明',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '审批状态:0-待审批,1-已通过,2-已打回',
  `del_flag` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '申请提交时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后状态更新时间',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_fund_project` FOREIGN KEY (`project_id`) REFERENCES `research_project` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_fund_applicant` FOREIGN KEY (`applicant_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `chk_fund_amount` CHECK (`amount` > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='经费报销申请表';