package com.acha.project.model.vo.project.audit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(description = "项目审批日志分页记录")
public class ProjectAuditLogVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "动作描述")
    private String action;

    @Schema(description = "审批状态：0-提交/待审，1-通过，2-驳回")
    private Integer auditStatus;

    @Schema(description = "备注/审核意见")
    private String remark;

    @Schema(description = "操作人姓名")
    private String operatorName;

    @Schema(description = "操作时间")
    private Date createTime;

    @Schema(description = "业务ID")
    private Long businessId;
}
