package com.acha.project.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "project_task")
@Schema(description = "项目任务进度实体")
public class ProjectTask implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "任务ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "所属项目ID")
    @TableField("project_id")
    private Long projectId;

    @Schema(description = "任务派发人ID(通常是负责人)")
    @TableField("assigner_id")
    private Long assignerId;

    @Schema(description = "被分配执行的成员ID")
    @TableField("assignee_id")
    private Long assigneeId;

    @Schema(description = "任务简短标题")
    @TableField("task_name")
    private String taskName;

    @Schema(description = "任务详细描述(要求、目标等)")
    @TableField("task_description")
    private String taskDescription;

    @Schema(description = "任务截止时间(非常重要)")
    @TableField("deadline")
    private Date deadline;

    @Schema(description = "任务状态: 0-待开始, 1-进行中, 2-已完成")
    @TableField("status")
    private Integer status;

    @Schema(description = "逻辑删除：0-正常，1-已删除")
    @TableLogic
    @TableField("del_flag")
    private Integer delFlag;

    @Schema(description = "派发时间")
    @TableField("create_time")
    private Date createTime;

    @Schema(description = "最后更新时间")
    @TableField("update_time")
    private Date updateTime;
}
