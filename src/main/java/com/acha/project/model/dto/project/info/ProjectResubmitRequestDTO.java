package com.acha.project.model.dto.project.info;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Schema(description = "项目修改后重提请求")
public class ProjectResubmitRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "projectId 不能为空")
    @Min(value = 1, message = "projectId 必须大于 0")
    @Schema(description = "原项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long projectId;

    @NotBlank(message = "projectName 不能为空")
    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String projectName;

    @NotBlank(message = "description 不能为空")
    @Schema(description = "项目简介", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @NotNull(message = "budget 不能为空")
    @DecimalMin(value = "0.01", message = "budget 必须大于 0")
    @Schema(description = "预算金额", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal budget;

    @Schema(description = "重提说明")
    private String resubmitReason;
}
