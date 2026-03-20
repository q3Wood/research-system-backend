package com.acha.project.model.dto.project.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.io.Serializable;

@Data
@Schema(description = "项目添加成员请求参数")
public class ProjectMemberAddRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "项目ID")
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    @Schema(description = "需添加的用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "项目内角色 (如：核心骨干、科研助理等)")
    private String memberRole = "普通成员";
}
