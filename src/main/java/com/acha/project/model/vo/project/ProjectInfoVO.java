package com.acha.project.model.vo.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(description = "项目信息列表视图")
public class ProjectInfoVO {

    @Schema(description = "项目ID")
    private Long id;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "项目简介")
    private String description;

    @Schema(description = "项目总预算(元)")
    private BigDecimal budget;

    @Schema(description = "可用余额(元)")
    private BigDecimal balance;

    @Schema(description = "状态:0-申报待审,1-执行中,2-已结题,3-已驳回")
    private Integer status;

    @Schema(description = "项目负责人ID")
    private Long leaderId;

    @Schema(description = "项目负责人真实姓名")
    private String leaderName;

    @Schema(description = "申报时间")
    private Date createTime;

    @Schema(description = "最后修改时间")
    private Date updateTime;
}