package com.acha.project.service;

import com.acha.project.model.dto.project.audit.ProjectAuditLogQueryRequestDTO;
import com.acha.project.model.vo.project.audit.ProjectAuditLogVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface ProjectAuditLogService {

    /**
     * 按项目+模块分页查询审批日志
     */
    Page<ProjectAuditLogVO> pageAuditLogs(ProjectAuditLogQueryRequestDTO requestDTO);
}
