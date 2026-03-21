package com.acha.project.mapper;

import com.acha.project.model.dto.statistics.StatisticsQueryRequestDTO;
import com.acha.project.model.vo.statistics.AchievementTrendVO;
import com.acha.project.model.vo.statistics.FundTrendVO;
import com.acha.project.model.vo.statistics.OverviewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface StatisticsMapper {

    /**
     * 获取概览统计数据
     */
    OverviewVO getOverviewStatistic(@Param("dto") StatisticsQueryRequestDTO requestDTO);

    /**
     * 获取经费趋势数据
     */
    List<FundTrendVO> getFundTrend(@Param("dto") StatisticsQueryRequestDTO requestDTO);

    /**
     * 获取成果趋势数据
     */
    List<AchievementTrendVO> getAchievementTrend(@Param("dto") StatisticsQueryRequestDTO requestDTO);
}