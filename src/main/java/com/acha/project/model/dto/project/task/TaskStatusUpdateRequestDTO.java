package com.acha.project.model.dto.project.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 任务进度更新请求体
 */
@Data
@Schema(description = "项目任务状态更新请求对象")
public class TaskStatusUpdateRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "任务ID不能为空")
    @Schema(description = "要更新的任务ID")
    private Long id;

    @NotNull(message = "状态不能为空")
    @Min(value = 0, message = "状态值最小为0")
    @Max(value = 2, message = "状态值最大为2")
    @Schema(description = "新状态: 0-待开始, 1-进行中, 2-已完成")
    private Integer status;
}