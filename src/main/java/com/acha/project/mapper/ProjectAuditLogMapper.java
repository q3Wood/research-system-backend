package com.acha.project.mapper;

import com.acha.project.model.entity.ProjectAuditLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 全局审批历史流水表数据库操作接口
 */
public interface ProjectAuditLogMapper extends BaseMapper<ProjectAuditLog> {

    /**
     * 新增审核流水记录（自定义 SQL）
     * @param auditLog 流水实体
     * @return 影响行数
     */
    int insertAuditLog(ProjectAuditLog auditLog);
}
