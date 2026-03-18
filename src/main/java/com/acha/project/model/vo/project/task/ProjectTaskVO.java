package com.acha.project.model.vo.project.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

@Data
@Schema(description = "项目任务详情视图")
public class ProjectTaskVO {

    @Schema(description = "任务ID")
    private Long id;

    @Schema(description = "所属项目ID")
    private Long projectId;

    @Schema(description = "任务简短标题")
    private String taskName;

    @Schema(description = "任务详细描述")
    private String taskDescription;

    @Schema(description = "任务状态: 0-待开始, 1-进行中, 2-已完成")
    private Integer status;

    @Schema(description = "任务截止时间")
    private Date deadline;
    
    @Schema(description = "是否已逾期: true=逾期, false=正常")
    private Boolean isOverdue;

    @Schema(description = "任务派发人ID")
    private Long assignerId;

    @Schema(description = "任务派发人真实姓名")
    private String assignerName;

    @Schema(description = "任务接收人ID")
    private Long assigneeId;

    @Schema(description = "任务接收人真实姓名")
    private String assigneeName;

    @Schema(description = "派发时间")
    private Date createTime;

    @Schema(description = "最后更新时间")
    private Date updateTime;
}