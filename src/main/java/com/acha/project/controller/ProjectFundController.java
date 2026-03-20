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

    private final com.acha.project.service.ProjectFundRecordService projectFundRecordService;

    public ProjectFundController(com.acha.project.service.ProjectFundRecordService projectFundRecordService) {
        this.projectFundRecordService = projectFundRecordService;
    }

    @PostMapping("/apply")
    @Operation(summary = "项目成员提交报销申请")
    public BaseResponse<Long> applyFund(@org.springframework.validation.annotation.Validated @RequestBody com.acha.project.model.dto.project.fund.FundApplyRequestDTO requestDTO) {
        Long recordId = projectFundRecordService.applyFund(requestDTO);
        return BaseResponse.success(recordId);
    }

    @PostMapping("/audit")
    @Operation(summary = "负责人/管理员审批报销 (通过后触发自动扣款)")
    public BaseResponse<Boolean> auditFund(@org.springframework.validation.annotation.Validated @RequestBody com.acha.project.model.dto.project.fund.FundAuditRequestDTO requestDTO) {
        boolean result = projectFundRecordService.auditFund(requestDTO);
        return BaseResponse.success(result);
    }

    @PostMapping("/list/page/{projectId}")
    @Operation(summary = "分页查询项目所有经费申请")
    public BaseResponse<com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.acha.project.model.vo.project.fund.FundVO>> pageFundRecords(
            @PathVariable("projectId") Long projectId,
            @RequestBody com.acha.project.model.dto.project.fund.FundQueryRequestDTO requestDTO) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.acha.project.model.vo.project.fund.FundVO> page = 
                projectFundRecordService.pageFunds(projectId, requestDTO);
        return BaseResponse.success(page);
    }

    // TODO: 4. 查询”我“的报销申请记录 (Get)
}
