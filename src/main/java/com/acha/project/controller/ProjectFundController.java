package com.acha.project.controller;

import com.acha.project.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 项目经费管理接口
 */
@RestController
@RequestMapping("/project/fund")
@Slf4j
@Tag(name = "项目管理-经费报销", description = "经费申请、审批、记录查询接口")
public class ProjectFundController {

    @GetMapping("/test")
    @Operation(summary = "测试接口")
    public BaseResponse<String> test() {
        return BaseResponse.success("ProjectFundController 运行正常");
    }

    // TODO: 1. 成员提交报销申请 (Post)
    // TODO: 2. 负责人/管理员审批报销 (Post) - 审批通过后会触发自动扣款
    // TODO: 3. 查询项目的所有经费明细 (Get)
    // TODO: 4. 查询”我“的报销申请记录 (Get)
}
