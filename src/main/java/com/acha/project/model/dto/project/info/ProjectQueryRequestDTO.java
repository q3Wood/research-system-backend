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

    @Schema(description = "项目状态:0-待审,1-通过(执行中),2-驳回,3-已结题(可选)")
    private Integer status;

    @Schema(description = "项目负责人ID(用于查询某个人创建的所有项目)(可选)")
    private Long leaderId;
}
