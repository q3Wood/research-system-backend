package com.acha.project.controller;

import com.acha.project.common.BaseResponse;
import com.acha.project.model.dto.project.info.ProjectAddRequestDTO;
import com.acha.project.service.ProjectInfoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
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
    public BaseResponse<String> addProject(@RequestBody ProjectAddRequestDTO request) {
        String projectName = request.getProjectName();
        String projectDescription = request.getDescription();
        BigDecimal budget = request.getBudget();

        projectInfoService.addProject(projectName, projectDescription, budget);
        return BaseResponse.success("项目申报成功");
    }

}
