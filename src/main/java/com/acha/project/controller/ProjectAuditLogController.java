package com.acha.project.controller;

import com.acha.project.common.BaseResponse;
import com.acha.project.model.dto.project.audit.ProjectAuditLogQueryRequestDTO;
import com.acha.project.model.vo.project.audit.ProjectAuditLogVO;
import com.acha.project.service.ProjectAuditLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目审批日志查询接口
 */
@RestController
@RequestMapping("/project/audit/log")
@Slf4j
@Tag(name = "项目管理-审批日志", description = "按项目与模块分页查询审批时间轴")
public class ProjectAuditLogController {

    private final ProjectAuditLogService projectAuditLogService;

    public ProjectAuditLogController(ProjectAuditLogService projectAuditLogService) {
        this.projectAuditLogService = projectAuditLogService;
    }

    @GetMapping("/list")
    @Operation(summary = "分页查询审批日志", description = "按 projectId + moduleType 查询，默认按 createTime 倒序")
    public BaseResponse<Page<ProjectAuditLogVO>> listAuditLogs(@Validated ProjectAuditLogQueryRequestDTO requestDTO) {
        Page<ProjectAuditLogVO> page = projectAuditLogService.pageAuditLogs(requestDTO);
        return BaseResponse.success(page);
    }
}
