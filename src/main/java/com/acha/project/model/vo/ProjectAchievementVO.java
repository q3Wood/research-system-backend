package com.acha.project.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(description = "科研成果视图对象(带提交人姓名)")
public class ProjectAchievementVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "成果ID")
    private Long id;

    @Schema(description = "所属项目ID")
    private Long projectId;

    @Schema(description = "成果提交人ID")
    private Long submitterId;

    @Schema(description = "成果提交人真实姓名 (关联查出)")
    private String submitterName;

    @Schema(description = "成果标题")
    private String title;

    @Schema(description = "成果类型(论文、专利、软著等)")
    private String achievementType;

    @Schema(description = "附件物理存储路径/或者虚拟映射路径")
    private String fileUrl;

    @Schema(description = "验收状态:0-待验收,1-已通过,2-已打回")
    private Integer status;

    @Schema(description = "最新验收意见(从审计流水中查询)")
    private String auditRemark;

    @Schema(description = "提交时间(创建时间)")
    private Date createTime;
}
