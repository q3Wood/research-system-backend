package com.acha.project.service;

import com.acha.project.model.dto.statistics.StatisticsQueryRequestDTO;
import com.acha.project.model.vo.statistics.AchievementTrendVO;
import com.acha.project.model.vo.statistics.FundTrendVO;
import com.acha.project.model.vo.statistics.OverviewVO;

import java.util.List;

public interface StatisticsService {
    
    /**
     * 获取系统总览数据
     */
    OverviewVO getOverview(StatisticsQueryRequestDTO requestDTO);

    /**
     * 经费及申请趋势分析
     */
    List<FundTrendVO> getFundTrend(StatisticsQueryRequestDTO requestDTO);

    /**
     * 成果及趋势分析
     */
    List<AchievementTrendVO> getAchievementTrend(StatisticsQueryRequestDTO requestDTO);
}