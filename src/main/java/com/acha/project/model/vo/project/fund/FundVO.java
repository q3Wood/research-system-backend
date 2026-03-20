package com.acha.project.model.vo.project.fund;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(description = "经费查询记录VO")
public class FundVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "记录ID")
    private Long id;
    
    @Schema(description = "所属项目ID")
    private Long projectId;

    @Schema(description = "申请用途(对应实体类的usageDesc)")
    private String usageDesc;

    @Schema(description = "申请金额")
    private BigDecimal amount;
    
    @Schema(description = "申请人ID")
    private Long applicantId;
    
    @Schema(description = "申请人姓名")
    private String applicantName;
    
    @Schema(description = "审批状态：0-待审批，1-通过，2-驳回")
    private Integer status;
    
    @Schema(description = "审核意见")
    private String auditRemark;
    
    @Schema(description = "申请提交时间")
    private Date createTime;
}
