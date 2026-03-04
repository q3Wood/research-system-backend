package com.acha.project.controller;

import com.acha.project.common.BaseResponse;
import com.acha.project.model.dto.project.info.ProjectAddRequestDTO;
import com.acha.project.model.dto.project.info.ProjectAuditRequestDTO;
import com.acha.project.service.ProjectInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.*;

/**
 * 项目基本信息管理接口
 */
@RestController
@RequestMapping("/project")
@Slf4j
@Tag(name = "项目管理-基本信息", description = "项目申报、审批、查询接口")
public class ProjectInfoController {

    @Resource
    private ProjectInfoService projectInfoService;

    /**
     * 申报项目
     * @param request 项目申报请求参数
     * @return 申报结果
     */
    @PostMapping("/add")
    @Operation(summary = "项目申报", description = "科研人员提交项目申请，初始状态为待审批")
    public BaseResponse<String> addProject(@RequestBody @Valid ProjectAddRequestDTO request) {
        String projectName = request.getProjectName();
        String description = request.getDescription();
        BigDecimal budget = request.getBudget();

        projectInfoService.addProject(projectName, description, budget);
        return BaseResponse.success("项目申报成功");
    }

    /**
     * 审核项目 (管理员/专家)
     * @param request 审核请求参数(项目ID, 状态, 意见)
     * @return 审核结果
     */
    @PostMapping("/audit")
    @Operation(summary = "项目审核", description = "管理员审批项目：通过(1) 或 驳回(3)")
    public BaseResponse<String> auditProject(@RequestBody @Valid ProjectAuditRequestDTO request) {
        projectInfoService.auditProject(request);
        return BaseResponse.success("审核操作成功");
    }

}
