package com.acha.project.model.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.io.Serializable;

@Data
@Schema(description = "通用审批请求参数")
public class CommonAuditRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "业务ID(项目ID / 经费记录ID / 成果ID)")
    @NotNull(message = "业务ID不能为空")
    private Long businessId;

    @Schema(description = "模块类型：1-项目，2-经费，3-成果")
    @NotNull(message = "模块类型不能为空")
    private Integer moduleType;

    @Schema(description = "审批结果状态: 1-通过(执行中), 2-驳回")
    @NotNull(message = "审批结果不能为空")
    private Integer auditStatus;

    @Schema(description = "审批意见 / 驳回理由")
    private String remark;
}
