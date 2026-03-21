package com.acha.project.model.dto.project.info;

import lombok.Data;


import java.io.Serializable;

import jakarta.validation.constraints.NotNull;

/**
 * 项目审核请求 DTO
 */
@Data
public class ProjectAuditRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 要审核的项目 ID
     */
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    /**
     * 审核结果状态
     * 1 - 审核通过 (变为执行中)
        * 2 - 审核驳回 (变为已驳回)
     */
    @NotNull(message = "审核结果状态不能为空")
    private Integer auditStatus;

    /**
     * 审核意见 / 驳回理由 (选填)
     */
    private String remark;
}
