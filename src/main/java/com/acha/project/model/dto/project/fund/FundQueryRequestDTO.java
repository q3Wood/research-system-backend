package com.acha.project.model.dto.project.fund;

import com.acha.project.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "经费查询请求DTO")
public class FundQueryRequestDTO extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "状态：0-待审批，1-通过，2-驳回(不传或传空查所有)")
    private Integer status;
}
