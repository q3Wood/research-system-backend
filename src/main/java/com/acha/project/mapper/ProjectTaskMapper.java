package com.acha.project.mapper;

import com.acha.project.model.entity.ProjectTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 项目任务进度表数据库操作接口
 */
public interface ProjectTaskMapper extends BaseMapper<ProjectTask> {
    
    /**
     * 新增项目任务（自定义 SQL）
     * @param projectTask 任务实体
     * @return 影响行数
     */
    int addTask(ProjectTask projectTask);
}

