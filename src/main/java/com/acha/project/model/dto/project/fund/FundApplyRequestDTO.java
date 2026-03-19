package com.acha.project.model.dto.project.fund;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 经费报销申请 DTO
 */
@Data
@Schema(description = "经费报销申请请求入参")
public class FundApplyRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "所属项目ID")
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    @Schema(description = "申请金额(元)")
    @NotNull(message = "申请金额不能为空")
    @DecimalMin(value = "0.01", message = "报销金额必须大于0")
    private BigDecimal amount;

    @Schema(description = "资金用途详细说明")
    @NotBlank(message = "资金用途不能为空")
    private String usageDesc;
}