package com.acha.project.service.impl;

import com.acha.project.common.ErrorCode;
import com.acha.project.common.UserContext;
import com.acha.project.exception.BusinessException;
import com.acha.project.mapper.ProjectInfoMapper;
import com.acha.project.mapper.ProjectMemberMapper;
import com.acha.project.mapper.ProjectTaskMapper;
import com.acha.project.model.dto.project.task.ProjectTaskAssignRequestDTO;
import com.acha.project.model.entity.ProjectInfo;
import com.acha.project.model.entity.ProjectTask;
import com.acha.project.service.ProjectTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ProjectTaskServiceImpl extends ServiceImpl<ProjectTaskMapper, ProjectTask> implements ProjectTaskService {

    @Resource
    private ProjectTaskMapper projectTaskMapper;

    @Resource
    private ProjectInfoMapper projectInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignTask(ProjectTaskAssignRequestDTO request) {
        // 1. 获取当前登录用户（派发人）
        if (UserContext.get() == null || UserContext.get().getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        Long assignerId = UserContext.get().getId();

        // 2. 校验项目是否存在
        ProjectInfo project = projectInfoMapper.getProjectById(request.getProjectId());
        if (project == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "关联的项目不存在");
        }

        // 3. 校验权限：只有项目负责人可以派发任务 (可选，根据需求严格程度)
        // 注意：ProjectInfo 的 getProjectById 并没有包含成员列表，这里只简单校验是否是负责人
        if (!project.getLeaderId().equals(assignerId)) {
            // 这里为了演示严谨性加上校验，如果只是演示全员可操作可去掉
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "只有项目负责人可以派发任务");
        }
        
        // 4. 构建任务实体
        ProjectTask task = new ProjectTask();
        task.setProjectId(request.getProjectId());
        task.setAssignerId(assignerId);
        task.setAssigneeId(request.getAssigneeId());
        task.setTaskName(request.getTaskName());
        task.setTaskDescription(request.getTaskDescription());
        task.setDeadline(request.getDeadline());
        task.setStatus(0); // 0-待开始

        // 5. 保存任务
        projectTaskMapper.addTask(task);
    }
}
