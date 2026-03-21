package com.acha.project.model.vo.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "项目重提结果")
public class ProjectResubmitVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "项目状态")
    private Integer status;

    @Schema(description = "状态文案")
    private String statusText;
}
