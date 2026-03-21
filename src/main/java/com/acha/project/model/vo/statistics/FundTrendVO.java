package com.acha.project.model.vo.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Schema(description = "经费趋势明细项")
public class FundTrendVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "日期")
    private String date; 
    
    @Schema(description = "申请总数")
    private Long pendingCount = 0L;
    
    @Schema(description = "报销通过总数")
    private Long approvedCount = 0L;
    
    @Schema(description = "申请总金额")
    private BigDecimal pendingAmount = BigDecimal.ZERO;
    
    @Schema(description = "通过总金额")
    private BigDecimal approvedAmount = BigDecimal.ZERO;
}