package com.acha.project.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "更改用户角色请求")
public class UserUpdateRoleRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "新角色 (0-科研人员, 1-普通管理员, 2-超级管理员)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "角色不能为空")
    private Integer role;
}