package com.acha.project.controller;

import com.acha.project.common.BaseResponse;
import com.acha.project.model.dto.project.achievement.AchievementAddRequestDTO;
import com.acha.project.service.ProjectAchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 项目成果管理接口
 */
@RestController
@RequestMapping("/project/achievement")
@Slf4j
@Tag(name = "项目管理-科研成果", description = "成果归档、验收、查询接口")
public class ProjectAchievementController {

    private final ProjectAchievementService projectAchievementService;

    public ProjectAchievementController(ProjectAchievementService projectAchievementService) {
        this.projectAchievementService = projectAchievementService;
    }

    @PostMapping("/submit")
    @Operation(summary = "阶段二：提交科研成果(包含表单信息与附件名称)")
    public BaseResponse<Long> submitAchievement(@Validated @RequestBody AchievementAddRequestDTO requestDTO) {
        Long achievementId = projectAchievementService.submitAchievement(requestDTO);
        return BaseResponse.success(achievementId);
    }

    @PostMapping("/audit")
    @Operation(summary = "指导老师/管理员：审核验收科研成果")
    public BaseResponse<Boolean> auditAchievement(@Validated @RequestBody com.acha.project.model.dto.project.achievement.AchievementAuditRequestDTO requestDTO) {
        boolean result = projectAchievementService.auditAchievement(requestDTO);
        return BaseResponse.success(result);
    }

    @PostMapping("/list/page/{projectId}")
    @Operation(summary = "分页查询项目下的科研成果(带提交人姓名)")
    public BaseResponse<com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.acha.project.model.vo.ProjectAchievementVO>> listAchievementsByPage(
            @PathVariable("projectId") Long projectId,
            @RequestBody com.acha.project.common.PageRequest pageRequest) {
        if (pageRequest == null) {
            pageRequest = new com.acha.project.common.PageRequest();
        }
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.acha.project.model.vo.ProjectAchievementVO> result = 
            projectAchievementService.listAchievementsByPage(projectId, pageRequest);
        return BaseResponse.success(result);
    }

    // TODO: 4. 按类型分类统计查询成果 (Get)
}
