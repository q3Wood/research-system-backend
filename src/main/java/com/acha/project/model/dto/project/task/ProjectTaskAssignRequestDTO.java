package com.acha.project.model.dto.project.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 项目任务分配请求 DTO
 */
@Data
public class ProjectTaskAssignRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 所属项目 ID
     */
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    /**
     * 被分配执行的成员 ID
     */
    @NotNull(message = "被分配人ID不能为空")
    private Long assigneeId;

    /**
     * 任务简短标题
     */
    @NotBlank(message = "任务名称不能为空")
    private String taskName;

    /**
     * 任务详细描述
     */
    private String taskDescription;

    /**
     * 任务截止时间
     */
    @Future(message = "截止时间必须是将来的时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deadline;
}
