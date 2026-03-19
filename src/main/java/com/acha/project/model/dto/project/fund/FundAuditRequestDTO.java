package com.acha.project.model.dto.project.fund;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.io.Serializable;

@Data
@Schema(description = "经费审批请求参数")
public class FundAuditRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "经费报销申请记录ID")
    @NotNull(message = "报销记录ID不能为空")
    private Long id;

    @Schema(description = "审批结果状态: 1-通过, 2-驳回")
    @NotNull(message = "审批结果不能为空")
    private Integer status;

    @Schema(description = "审批意见/驳回理由")
    private String remark;
}
