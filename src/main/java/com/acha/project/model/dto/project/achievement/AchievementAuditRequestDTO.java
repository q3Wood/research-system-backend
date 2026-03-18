package com.acha.project.model.dto.project.achievement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 成果审核验收请求 DTO
 */
@Data
@Schema(description = "成果审核请求入参")
public class AchievementAuditRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "成果ID")
    @NotNull(message = "成果ID不能为空")
    private Long id;

    @Schema(description = "审核结果: 1-已通过, 2-已打回")
    @NotNull(message = "审核结果不能为空(1或2)")
    private Integer status;

    @Schema(description = "审核意见/打回理由 (如果打回，此项必填)")
    private String remark;
}
