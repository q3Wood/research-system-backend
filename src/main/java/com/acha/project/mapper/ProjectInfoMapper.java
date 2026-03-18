package com.acha.project.mapper;

import com.acha.project.model.entity.ProjectInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 科研项目主表数据库操作接口
 */
public interface ProjectInfoMapper extends BaseMapper<ProjectInfo> {
    
    /**
     * 新增科研项目（自定义 SQL）
     * @param projectInfo 项目实体
     * @return 影响行数
     */
    int addProject(ProjectInfo projectInfo);

    /**
     * 根据主键查询项目（自定义 SQL）
     * @param id 项目 ID
     * @return 项目实体
     */
    ProjectInfo getProjectById(@Param("id") Long id);

    /**
     * 更新项目状态（自定义 SQL）
     * @param id 项目 ID
     * @param status 新状态
     * @return 影响行数
     */
    int updateProjectStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 分页查询：统计总数
     * 普通用户(role=0)只能看见自己的任务，管理员可以看见全部
     */
    long countProjects(@Param("requestDTO") com.acha.project.model.dto.project.info.ProjectQueryRequestDTO requestDTO, 
                       @Param("userId") Long userId, 
                       @Param("role") Integer role);

    /**
     * 分页查询：获取含负责人姓名的列表
     */
    java.util.List<com.acha.project.model.vo.project.ProjectInfoVO> listProjectsByPage(
            @Param("requestDTO") com.acha.project.model.dto.project.info.ProjectQueryRequestDTO requestDTO, 
            @Param("userId") Long userId, 
            @Param("role") Integer role, 
            @Param("offset") long offset, 
            @Param("limit") long limit);
}
