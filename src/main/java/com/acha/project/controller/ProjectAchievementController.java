package com.acha.project.controller;

import com.acha.project.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 项目成果管理接口
 */
@RestController
@RequestMapping("/project/achievement")
@Slf4j
@Tag(name = "项目管理-科研成果", description = "成果归档、验收、查询接口")
public class ProjectAchievementController {

    @GetMapping("/test")
    @Operation(summary = "测试接口")
    public BaseResponse<String> test() {
        return BaseResponse.success("ProjectAchievementController 运行正常");
    }

    // TODO: 1. 提交科研成果 (Post)
    // TODO: 2. 审核验收成果 (Post)
    // TODO: 3. 项目全部成果列表 (Get)
    // TODO: 4. 按类型分类统计查询成果 (Get)
}
