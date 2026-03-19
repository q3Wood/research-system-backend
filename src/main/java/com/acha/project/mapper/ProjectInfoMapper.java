package com.acha.project.mapper;

import com.acha.project.model.entity.ProjectInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface ProjectInfoMapper extends BaseMapper<ProjectInfo> {

    int addProject(ProjectInfo projectInfo);

    ProjectInfo getProjectById(@Param("id") Long id);

    int updateProjectStatus(@Param("id") Long id, @Param("status") Integer status);

    long countProjects(@Param("requestDTO") com.acha.project.model.dto.project.info.ProjectQueryRequestDTO requestDTO,
                       @Param("userId") Long userId,
                       @Param("role") Integer role);

    java.util.List<com.acha.project.model.vo.project.ProjectInfoVO> listProjectsByPage(
            @Param("requestDTO") com.acha.project.model.dto.project.info.ProjectQueryRequestDTO requestDTO,
            @Param("userId") Long userId,
            @Param("role") Integer role,
            @Param("offset") long offset,
            @Param("limit") long limit);

    int deductProjectBalance(@Param("projectId") Long projectId, @Param("amount") java.math.BigDecimal amount);
}
