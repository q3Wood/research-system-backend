package com.acha.project.model.vo.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

@Data
@Schema(description = "成果趋势明细项")
public class AchievementTrendVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "日期")
    private String date; 
    
    @Schema(description = "提交总数")
    private Long submitCount = 0L;
    
    @Schema(description = "待审核数")
    private Long pendingCount = 0L;
    
    @Schema(description = "已通过数")
    private Long approvedCount = 0L;
    
    @Schema(description = "已驳回数")
    private Long rejectedCount = 0L;
}