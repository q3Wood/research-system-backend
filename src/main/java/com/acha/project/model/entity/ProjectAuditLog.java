package com.acha.project.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "project_audit_log")
@Schema(description = "全局审批历史流水实体")
public class ProjectAuditLog implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "流水主键")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "归属的项目ID")
    @TableField("project_id")
    private Long projectId;

    @Schema(description = "业务模块: 1-项目申报, 2-经费报销, 3-成果验收")
    @TableField("module_type")
    private Integer moduleType;

    @Schema(description = "具体业务的ID(如果是经费，这里存经费记录的id)")
    @TableField("business_id")
    private Long businessId;

    @Schema(description = "操作人ID(申请人或审核人)")
    @TableField("operator_id")
    private Long operatorId;

    @Schema(description = "动作描述(如: 提交申请, 审核通过, 审核驳回)")
    @TableField("action")
    private String action;

    @Schema(description = "操作说明/审核意见(如驳回理由)")
    @TableField("remark")
    private String remark;

    @Schema(description = "操作发生时间")
    @TableField("create_time")
    private Date createTime;
}
