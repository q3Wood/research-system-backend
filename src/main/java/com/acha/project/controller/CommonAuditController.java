package com.acha.project.controller;

import com.acha.project.common.BaseResponse;
import com.acha.project.model.dto.common.CommonAuditRequestDTO;
import com.acha.project.service.CommonAuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 统一审批接口 (项目/经费/成果 均走此接口)
 */
@RestController
@RequestMapping("/common/audit")
@Slf4j
@Tag(name = "公共模块-统一审批", description = "支持项目、经费、成果等模块的统一审批流转")
public class CommonAuditController {

    private final CommonAuditService commonAuditService;

    public CommonAuditController(CommonAuditService commonAuditService) {
        this.commonAuditService = commonAuditService;
    }

    @PostMapping
    @Operation(summary = "提交审批结果 (适用于所有模块)")
    public BaseResponse<Boolean> audit(@Validated @RequestBody CommonAuditRequestDTO requestDTO) {
        boolean result = commonAuditService.audit(requestDTO);
        return BaseResponse.success(result);
    }
}
