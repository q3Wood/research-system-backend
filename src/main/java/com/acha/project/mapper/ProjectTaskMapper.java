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

    /**
     * 查询某项目的任务列表，带有发布人和执行人的真实姓名，以及是否逾期标志
     */
    java.util.List<com.acha.project.model.vo.project.task.ProjectTaskVO> listTasksWithNamesByProjectId(@org.apache.ibatis.annotations.Param("projectId") Long projectId);

    /**
     * 更新任务状态
     */
    int updateTaskStatus(@org.apache.ibatis.annotations.Param("id") Long id, @org.apache.ibatis.annotations.Param("status") Integer status);

    /**
     * 根据主键查询单条任务
     */
    ProjectTask getTaskById(@org.apache.ibatis.annotations.Param("id") Long id);
}

