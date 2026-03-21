package com.acha.project.controller;

import com.acha.project.common.BaseResponse;
import com.acha.project.model.dto.project.info.ProjectAddRequestDTO;
import com.acha.project.model.dto.project.info.ProjectAuditRequestDTO;
import com.acha.project.model.dto.project.info.ProjectDeleteRequestDTO;
import com.acha.project.model.dto.project.info.ProjectResubmitRequestDTO;
import com.acha.project.model.vo.project.ProjectResubmitVO;
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
    @Operation(summary = "项目审核", description = "管理员审批项目：通过(1) 或 驳回(2)")
    public BaseResponse<String> auditProject(@RequestBody @Valid ProjectAuditRequestDTO request) {
        projectInfoService.auditProject(request);
        return BaseResponse.success("审核操作成功");
    }

    /**
     * 删除项目（逻辑删除）
     */
    @PostMapping("/delete")
    @Operation(summary = "删除项目", description = "仅负责人或管理员可删除，且仅允许删除待审(0)或驳回(2)项目")
    public BaseResponse<Boolean> deleteProject(@RequestBody @Valid ProjectDeleteRequestDTO request) {
        boolean result = projectInfoService.deleteProject(request);
        return BaseResponse.success(result);
    }

    /**
     * 修改后重提（更新原项目，不新建ID）
     */
    @PostMapping("/resubmit")
    @Operation(summary = "项目修改后重提", description = "仅负责人或管理员可将驳回项目重提为待审")
    public BaseResponse<ProjectResubmitVO> resubmitProject(@RequestBody @Valid ProjectResubmitRequestDTO request) {
        ProjectResubmitVO result = projectInfoService.resubmitProject(request);
        return BaseResponse.success(result, "重提成功");
    }

    /**
     * 分页查询项目列表
     * @param request 分页与查询条件
     * @return 带分页的项目列表
     */
    @PostMapping("/list/page")
    @Operation(summary = "项目列表分页查询", description = "普通用户只能看自己申请的项目，管理员能看所有项目")
    public BaseResponse<com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.acha.project.model.vo.project.ProjectInfoVO>> listProjectByPage(
            @RequestBody com.acha.project.model.dto.project.info.ProjectQueryRequestDTO request) {
        return BaseResponse.success(projectInfoService.pageProjects(request));
    }

    /**
     * 根据主键获取科研项目详细信息
     * @param id 项目的主键ID
     * @return 项目详细信息VO (含负责人姓名)
     */
    @GetMapping("/get")
    @Operation(summary = "获取单个项目详情", description = "通过ID获取连表查询后的详细项目信息")
    public BaseResponse<com.acha.project.model.vo.project.ProjectInfoVO> getProjectById(@RequestParam("id") Long id) {
        return BaseResponse.success(projectInfoService.getProjectDetailById(id));
    }

}
