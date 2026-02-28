package com.acha.project.controller;

import com.acha.project.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 项目基本信息管理接口
 */
@RestController
@RequestMapping("/project")
@Slf4j
@Tag(name = "项目管理-基本信息", description = "项目申报、审批、查询接口")
public class ProjectInfoController {

    @GetMapping("/test")
    @Operation(summary = "测试接口")
    public BaseResponse<String> test() {
        return BaseResponse.success("ProjectInfoController 运行正常");
    }

    // TODO: 1. 项目申报 (Post)
    // TODO: 2. 项目分页列表查询 (Get)
    // TODO: 3. 项目详情查询 (Get)
    // TODO: 4. 管理员审批项目 (Post)
    // TODO: 5. 项目结题 (Post)
}
