package com.acha.project.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "project_info")
@Schema(description = "科研项目主实体")
public class ProjectInfo implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "项目名称")
    @TableField("project_name")
    private String projectName;

    @Schema(description = "项目简介")
    @TableField("description")
    private String description;

    @Schema(description = "项目总预算(元)")
    @TableField("budget")
    private BigDecimal budget;

    @Schema(description = "可用余额(元)")
    @TableField("balance")
    private BigDecimal balance;

    @Schema(description = "状态:0-待审,1-通过(执行中),2-驳回,3-已结题")
    @TableField("status")
    private Integer status;

    @Schema(description = "项目负责人ID")
    @TableField("leader_id")
    private Long leaderId;

    @Schema(description = "逻辑删除标志：0-正常，1-已删除")
    @TableLogic
    @TableField("del_flag")
    private Integer delFlag;

    @Schema(description = "申报时间")
    @TableField("create_time")
    private Date createTime;

    @Schema(description = "最后修改时间")
    @TableField("update_time")
    private Date updateTime;
}
