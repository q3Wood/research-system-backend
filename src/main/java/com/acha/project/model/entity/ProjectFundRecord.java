package com.acha.project.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "project_fund_record")
@Schema(description = "经费报销申请实体")
public class ProjectFundRecord implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "流水ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "所属项目ID")
    @TableField("project_id")
    private Long projectId;

    @Schema(description = "申请人ID(谁要花这笔钱)")
    @TableField("applicant_id")
    private Long applicantId;

    @Schema(description = "申请金额(元)")
    @TableField("amount")
    private BigDecimal amount;

    @Schema(description = "资金用途详细说明")
    @TableField("usage_desc")
    private String usageDesc;

    @Schema(description = "审批状态:0-待审批,1-已通过,2-已打回")
    @TableField("status")
    private Integer status;

    @Schema(description = "逻辑删除：0-正常，1-已删除")
    @TableLogic
    @TableField("del_flag")
    private Integer delFlag;

    @Schema(description = "申请提交时间")
    @TableField("create_time")
    private Date createTime;

    @Schema(description = "最后状态更新时间")
    @TableField("update_time")
    private Date updateTime;
}
