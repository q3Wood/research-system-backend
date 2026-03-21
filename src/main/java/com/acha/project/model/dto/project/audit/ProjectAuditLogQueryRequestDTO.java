package com.acha.project.model.dto.project.audit;

import com.acha.project.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "项目审批日志分页查询请求")
public class ProjectAuditLogQueryRequestDTO extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "projectId 不能为空")
    @Min(value = 1, message = "projectId 必须大于 0")
    private Long projectId;

    @Schema(description = "业务模块: 1-项目申报, 2-经费报销, 3-成果验收", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "moduleType 不能为空")
    @Min(value = 1, message = "moduleType 仅支持 1/2/3")
    @Max(value = 3, message = "moduleType 仅支持 1/2/3")
    private Integer moduleType;
}
