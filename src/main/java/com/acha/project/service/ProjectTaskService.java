package com.acha.project.service;

import com.acha.project.model.dto.project.task.ProjectTaskAssignRequestDTO;
import com.acha.project.model.entity.ProjectTask;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProjectTaskService extends IService<ProjectTask> {

    /**
     * 分派项目任务
     *
     * @param request 任务分配请求
     */
    void assignTask(ProjectTaskAssignRequestDTO request);
}
