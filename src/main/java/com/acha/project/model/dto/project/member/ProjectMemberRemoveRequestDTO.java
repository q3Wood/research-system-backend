package com.acha.project.model.dto.project.member;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 移除项目成员请求参数
 */
@Data
public class ProjectMemberRemoveRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目ID
     */
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    /**
     * 要移除的用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

}
