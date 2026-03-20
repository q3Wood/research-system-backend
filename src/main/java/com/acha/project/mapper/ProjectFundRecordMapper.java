package com.acha.project.mapper;

import com.acha.project.model.entity.ProjectFundRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface ProjectFundRecordMapper extends BaseMapper<ProjectFundRecord> {
    
    int insertFundRecord(ProjectFundRecord record);

    ProjectFundRecord getFundRecordById(@Param("id") Long id);

    int updateFundRecordStatus(@Param("id") Long id, @Param("status") Integer status);

    Long getProjectLeaderIdByFundId(@Param("fundId") Long fundId);

    long countFunds(@Param("projectId") Long projectId, @Param("requestDTO") com.acha.project.model.dto.project.fund.FundQueryRequestDTO requestDTO);

    java.util.List<com.acha.project.model.vo.project.fund.FundVO> listFundsByPage(
            @Param("projectId") Long projectId,
            @Param("offset") long offset,
            @Param("limit") long limit,
            @Param("requestDTO") com.acha.project.model.dto.project.fund.FundQueryRequestDTO requestDTO
    );
}
