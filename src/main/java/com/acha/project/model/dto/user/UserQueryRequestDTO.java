package com.acha.project.model.dto.user;

import com.acha.project.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequestDTO extends PageRequest {

    @Schema(description = "用户名或真实姓名(模糊查询,不传则不限制)")
    private String searchText;

    @Schema(description = "用户角色: 0-普通用户, 1-系统管理员(不传则查询所有角色)")
    private Integer role;
}
