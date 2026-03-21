package com.acha.project.service.impl;

import com.acha.project.mapper.StatisticsMapper;
import com.acha.project.model.dto.statistics.StatisticsQueryRequestDTO;
import com.acha.project.model.vo.statistics.AchievementTrendVO;
import com.acha.project.model.vo.statistics.FundTrendVO;
import com.acha.project.model.vo.statistics.OverviewVO;
import com.acha.project.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private StatisticsMapper statisticsMapper;

    @Override
    public OverviewVO getOverview(StatisticsQueryRequestDTO requestDTO) {
        OverviewVO vo = statisticsMapper.getOverviewStatistic(requestDTO);
        if (vo == null) {
            vo = new OverviewVO();
        }
        return vo;
    }

    @Override
    public List<FundTrendVO> getFundTrend(StatisticsQueryRequestDTO requestDTO) {
        return statisticsMapper.getFundTrend(requestDTO);
    }

    @Override
    public List<AchievementTrendVO> getAchievementTrend(StatisticsQueryRequestDTO requestDTO) {
        return statisticsMapper.getAchievementTrend(requestDTO);
    }
}