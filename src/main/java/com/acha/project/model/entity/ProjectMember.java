package com.acha.project.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "project_member")
@Schema(description = "项目团队成员实体")
public class ProjectMember implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "独立主键ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "项目ID")
    @TableField("project_id")
    private Long projectId;

    @Schema(description = "成员ID")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "项目内角色(如: 核心骨干, 科研助理)")
    @TableField("member_role")
    private String memberRole;

    @Schema(description = "状态标识：0-在组，1-已退出(软删除)")
    @TableLogic
    @TableField("del_flag")
    private Integer delFlag;

    @Schema(description = "加入团队时间")
    @TableField("join_time")
    private Date joinTime;

    @Schema(description = "最后修改时间")
    @TableField("update_time")
    private Date updateTime;
}
