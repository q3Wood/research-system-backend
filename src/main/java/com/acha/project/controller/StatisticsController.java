package com.acha.project.controller;

import com.acha.project.common.BaseResponse;
import com.acha.project.common.UserContext;
import com.acha.project.exception.BusinessException;
import com.acha.project.common.ErrorCode;
import com.acha.project.model.dto.user.LoginUserDTO;
import com.acha.project.model.dto.statistics.StatisticsQueryRequestDTO;
import com.acha.project.model.vo.statistics.AchievementTrendVO;
import com.acha.project.model.vo.statistics.FundTrendVO;
import com.acha.project.model.vo.statistics.OverviewVO;
import com.acha.project.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

@Tag(name = "StatisticsController", description = "数据统计分析接口")
@RestController
@RequestMapping("/statistics")
@Slf4j
public class StatisticsController {

    private void checkAdminRole() {
        LoginUserDTO currentUser = UserContext.get();
        if (currentUser == null || currentUser.getRole() == null || currentUser.getRole() == 0) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "仅管理员可查看统计数据");
        }
    }

    @Resource
    private StatisticsService statisticsService;

    @Operation(summary = "系统总览数据")
    @PostMapping("/overview")
    public BaseResponse<OverviewVO> getOverview(@RequestBody(required = false) StatisticsQueryRequestDTO requestDTO) {
        checkAdminRole();
        if (requestDTO == null) {
            requestDTO = new StatisticsQueryRequestDTO();
        }
        OverviewVO overview = statisticsService.getOverview(requestDTO);
        return BaseResponse.success(overview);
    }

    @Operation(summary = "经费申请及批准趋势分析")
    @PostMapping("/fund/trend")
    public BaseResponse<List<FundTrendVO>> getFundTrend(@RequestBody(required = false) StatisticsQueryRequestDTO requestDTO) {
        checkAdminRole();
        if (requestDTO == null) {
            requestDTO = new StatisticsQueryRequestDTO();
        }
        List<FundTrendVO> trendData = statisticsService.getFundTrend(requestDTO);
        return BaseResponse.success(trendData);
    }

    @Operation(summary = "成果提交及审核趋势分析")
    @PostMapping("/achievement/trend")
    public BaseResponse<List<AchievementTrendVO>> getAchievementTrend(@RequestBody(required = false) StatisticsQueryRequestDTO requestDTO) {
        checkAdminRole();
        if (requestDTO == null) {
            requestDTO = new StatisticsQueryRequestDTO();
        }
        List<AchievementTrendVO> trendData = statisticsService.getAchievementTrend(requestDTO);
        return BaseResponse.success(trendData);
    }
}