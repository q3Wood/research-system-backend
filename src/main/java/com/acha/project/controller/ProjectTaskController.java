package com.acha.project.controller;

import com.acha.project.common.BaseResponse;
import com.acha.project.model.dto.project.task.ProjectTaskAssignRequestDTO;
import com.acha.project.model.dto.project.task.TaskStatusUpdateRequestDTO;
import com.acha.project.model.vo.project.task.ProjectTaskVO;
import com.acha.project.service.ProjectTaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 项目任务管理接口
 */
@RestController
@RequestMapping("/project/task")
@Slf4j
@Tag(name = "项目管理-任务分配", description = "任务派发、进度更新、查询接口")
public class ProjectTaskController {

    @Resource
    private ProjectTaskService projectTaskService;

    @PostMapping("/assign")
    @Operation(summary = "任务派发", description = "将项目任务分配给指定的员工")
    public BaseResponse<String> assignTask(@RequestBody @Valid ProjectTaskAssignRequestDTO request) {
        projectTaskService.assignTask(request);
        return BaseResponse.success("任务派发成功");
    }

    @PostMapping("/updateStatus")
    @Operation(summary = "更新任务进度", description = "执行人员汇报任务进度(开始做/已完成)")
    public BaseResponse<String> updateTaskStatus(@RequestBody @Valid TaskStatusUpdateRequestDTO request) {
        projectTaskService.updateTaskStatus(request);
        return BaseResponse.success("任务进度更新成功");
    }

    @GetMapping("/list/{projectId}")
    @Operation(summary = "查询项目下所有任务列表", description = "用于进度大盘展示计算，包含真实的执行人姓名和逾期状态计算")
    public BaseResponse<java.util.List<ProjectTaskVO>> listTasksByProjectId(@PathVariable("projectId") Long projectId) {
        return BaseResponse.success(projectTaskService.listTasksByProjectId(projectId));
    }
}
