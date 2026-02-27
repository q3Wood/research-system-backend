-- ==========================================================
-- 表名：research_achievement (科研成果归档表 - 终极版)
-- 作用：存储项目产出的各类成果及其附件路径，支持验收流转与留痕。
-- ==========================================================
-- ==========================================================
-- 表名：research_achievement (科研成果归档表 - 极简流转版)
-- 作用：存储成果信息与附件。审批留痕统一交由 project_audit_log 记录。
-- ==========================================================
CREATE TABLE `research_achievement` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '成果ID',
  `project_id` BIGINT NOT NULL COMMENT '所属项目ID',
  `submitter_id` BIGINT NOT NULL COMMENT '成果提交人ID',
  `title` VARCHAR(255) NOT NULL COMMENT '成果标题',
  `achievement_type` VARCHAR(50) NOT NULL COMMENT '成果类型(关联sys_config)',
  `file_url` VARCHAR(500) DEFAULT NULL COMMENT '附件物理存储路径',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '验收状态:0-待验收,1-已通过,2-已打回',
  
  -- 企业级审计字段 (只留最基础的，不侵入业务逻辑)
  `del_flag` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-正常，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_achieve_project` FOREIGN KEY (`project_id`) REFERENCES `research_project` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_achieve_submitter` FOREIGN KEY (`submitter_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `chk_achieve_status` CHECK (`status` IN (0, 1, 2)),
  CONSTRAINT `chk_achieve_del` CHECK (`del_flag` IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='科研项目成果表';