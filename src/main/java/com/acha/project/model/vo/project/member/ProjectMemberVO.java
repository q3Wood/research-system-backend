package com.acha.project.model.vo.project.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 项目成员记录查询视图对象
 */
@Data
@Schema(description = "项目成员列表视图对象")
public class ProjectMemberVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "记录主键ID")
    private Long id;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "成员的用户ID")
    private Long userId;

    @Schema(description = "用户登录账号")
    private String username;

    @Schema(description = "用户真实姓名")
    private String realName;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "项目内角色")
    private String memberRole;

    @Schema(description = "加入时间")
    private Date joinTime;
}
