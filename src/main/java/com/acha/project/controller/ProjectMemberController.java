package com.acha.project.controller;

import com.acha.project.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 项目成员管理接口
 */
@RestController
@RequestMapping("/project/member")
@Slf4j
@Tag(name = "项目管理-团队成员", description = "项目人员组建、邀请、移除接口")
public class ProjectMemberController {

    @GetMapping("/test")
    @Operation(summary = "测试接口")
    public BaseResponse<String> test() {
        return BaseResponse.success("ProjectMemberController 运行正常");
    }

    // TODO: 1. 添加成员入组 (Post)
    // TODO: 2. 移除成员 (Post)
    // TODO: 3. 获取项目下所有成员列表 (Get)
    // TODO: 4. 修改成员角色 (Post)
}
