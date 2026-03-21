package com.acha.project.model.vo.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Schema(description = "统计总览返回结果")
public class OverviewVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long projectTotal = 0L;
    private Long projectPending = 0L;
    private Long projectRunning = 0L;
    private Long projectClosed = 0L;
    
    private Long userTotal = 0L;
    
    private Long fundPendingCount = 0L;
    private BigDecimal fundPendingAmount = BigDecimal.ZERO;
    private BigDecimal fundApprovedAmount = BigDecimal.ZERO;
    
    private Long achievementTotal = 0L;
    private Long achievementPendingCount = 0L;
}