package com.acha.project.service;

import com.acha.project.model.dto.project.task.ProjectTaskAssignRequestDTO;
import com.acha.project.model.dto.project.task.TaskStatusUpdateRequestDTO;
import com.acha.project.model.vo.project.task.ProjectTaskVO;
import com.acha.project.model.entity.ProjectTask;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProjectTaskService extends IService<ProjectTask> {

    /**
     * 分派项目任务
     *
     * @param request 任务分配请求
     */
    void assignTask(ProjectTaskAssignRequestDTO request);

    /**
     * 更新任务状态
     */
    void updateTaskStatus(TaskStatusUpdateRequestDTO request);

    /**
     * 查询项目下的所有任务列表 (用于进度管理汇总展示)
     */
    java.util.List<ProjectTaskVO> listTasksByProjectId(Long projectId);
}
