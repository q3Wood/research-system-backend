package com.acha.project.model.dto.project.info;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "项目删除请求")
public class ProjectDeleteRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "projectId 不能为空")
    @Min(value = 1, message = "projectId 必须大于 0")
    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long projectId;

    @Schema(description = "删除原因(选填)")
    private String deleteReason;
}