package com.acha.project.model.dto.project.info;

import com.acha.project.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectQueryRequestDTO extends PageRequest {

    @Schema(description = "项目名称模糊搜索(可选)")
    private String searchText;

    @Schema(description = "项目状态:0-申报待审,1-执行中,2-已结题,3-已驳回(可选)")
    private Integer status;
}
