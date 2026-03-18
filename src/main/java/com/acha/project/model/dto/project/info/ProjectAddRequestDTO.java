package com.acha.project.model.dto.project.info;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 项目申报请求 DTO
 */
@Data
@Schema(description = "项目申报请求体")
public class ProjectAddRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "项目名称不能为空")
    @Size(max = 100, message = "项目名称长度不能超过100")
    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String projectName;

    @NotBlank(message = "项目简介不能为空")
    @Schema(description = "项目简介", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @NotNull(message = "项目总预算不能为空")
    @DecimalMin(value = "0.0", inclusive = false, message = "项目预算必须大于0")
    @Schema(description = "项目总预算(元)", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal budget;
}