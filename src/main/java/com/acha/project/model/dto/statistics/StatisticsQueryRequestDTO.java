package com.acha.project.model.dto.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

@Data
@Schema(description = "统计查询请求参数")
public class StatisticsQueryRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "开始日期 (yyyy-MM-dd)")
    private String startDate;

    @Schema(description = "结束日期 (yyyy-MM-dd)")
    private String endDate;

    @Schema(description = "粒度：day (目前仅支持天)")
    private String granularity = "day";
}