package com.acha.project.mapper;

import com.acha.project.model.entity.ProjectInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
}
