package com.acha.project.mapper;

import com.acha.project.model.entity.ProjectInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface ProjectInfoMapper extends BaseMapper<ProjectInfo> {

    int addProject(ProjectInfo projectInfo);

    ProjectInfo getProjectById(@Param("id") Long id);

    com.acha.project.model.vo.project.ProjectInfoVO getProjectVOById(@Param("id") Long id);

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

    int resubmitProject(@Param("projectId") Long projectId,
                        @Param("projectName") String projectName,
                        @Param("description") String description,
                        @Param("budget") java.math.BigDecimal budget);

    int logicalDeleteProject(@Param("projectId") Long projectId);

    int deductProjectBalance(@Param("projectId") Long projectId, @Param("amount") java.math.BigDecimal amount);
}
