package com.acha.project.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "project_achievement")
@Schema(description = "科研项目成果实体")
public class ProjectAchievement implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "成果ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "所属项目ID")
    @TableField("project_id")
    private Long projectId;

    @Schema(description = "成果提交人ID")
    @TableField("submitter_id")
    private Long submitterId;

    @Schema(description = "成果标题")
    @TableField("title")
    private String title;

    @Schema(description = "成果类型(关联sys_config)")
    @TableField("achievement_type")
    private String achievementType;

    @Schema(description = "附件物理存储路径")
    @TableField("file_url")
    private String fileUrl;

    @Schema(description = "验收状态:0-待验收,1-已通过,2-已打回")
    @TableField("status")
    private Integer status;

    @Schema(description = "逻辑删除：0-正常，1-已删除")
    @TableLogic
    @TableField("del_flag")
    private Integer delFlag;

    @Schema(description = "提交时间")
    @TableField("create_time")
    private Date createTime;

    @Schema(description = "最后更新时间")
    @TableField("update_time")
    private Date updateTime;
}
