package com.acha.project.model.dto.project.achievement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 成果提交请求 DTO
 */
@Data
@Schema(description = "成果提交请求入参")
public class AchievementAddRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "所属项目ID")
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    @Schema(description = "成果标题")
    @NotBlank(message = "成果标题不能为空")
    private String title;

    @Schema(description = "成果类型(如:论文 / 专利 / 软著等)")
    @NotBlank(message = "成果类型不能为空")
    private String achievementType;

    @Schema(description = "附件文件名称(前期上传接口返回的名字)")
    @NotBlank(message = "附件不能为空")
    private String fileUrl;
}
