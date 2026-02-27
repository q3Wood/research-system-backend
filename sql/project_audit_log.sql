-- ==========================================================
-- 表名：project_audit_log (项目审批历史流水表)
-- 作用：永久保存项目的每一次状态变更和审核意见，绝不丢失历史记录！
-- ==========================================================
-- ==========================================================
-- 表名：project_audit_log (全局通用审计流水表)
-- 作用：永久保存项目、经费、成果的每一次状态变更和审核意见！
-- ==========================================================
CREATE TABLE `project_audit_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '流水主键',
  
  -- 1. 核心关联 (为了前端能一把查出整个项目的所有时间轴)
  `project_id` BIGINT NOT NULL COMMENT '归属的项目ID',
  
  -- 2. 多态设计 (精确锁定具体是哪条数据在流转)
  `module_type` TINYINT NOT NULL COMMENT '业务模块: 1-项目申报, 2-经费报销, 3-成果验收',
  `business_id` BIGINT NOT NULL COMMENT '具体业务的ID(如果是经费，这里存经费记录的id)',
  
  -- 3. 操作详情
  `operator_id` BIGINT NOT NULL COMMENT '操作人ID(申请人或审核人)',
  `action` VARCHAR(50) NOT NULL COMMENT '动作描述(如: 提交申请, 审核通过, 审核驳回)', 
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '操作说明/审核意见(如驳回理由)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作发生时间',
  
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_log_project` FOREIGN KEY (`project_id`) REFERENCES `research_project` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_log_operator` FOREIGN KEY (`operator_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT
  
  -- 注意：这里不能对 business_id 加外键，因为它在不同模块下指向不同的表（这就叫多态）
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='全局审批历史流水表';