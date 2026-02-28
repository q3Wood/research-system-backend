package com.acha.project.controller;

import com.acha.project.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @GetMapping("/test")
    @Operation(summary = "测试接口")
    public BaseResponse<String> test() {
        return BaseResponse.success("ProjectTaskController 运行正常");
    }

    // TODO: 1. 负责人分配新任务 (Post)
    // TODO: 2. 成员更新任务状态 (Post)
    // TODO: 3. 查询某项目下的所有任务 (Get)
    // TODO: 4. 查询分配给”我“的任务 (Get)
}
