package com.acha.project.service.impl;

import com.acha.project.model.entity.ProjectInfo;

import org.springframework.stereotype.Service;

import com.acha.project.common.ErrorCode;
import com.acha.project.common.UserContext;
import com.acha.project.exception.BusinessException;
import com.acha.project.mapper.ProjectInfoMapper;
import com.acha.project.service.ProjectInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProjectInfoServiceImpl extends ServiceImpl<ProjectInfoMapper, ProjectInfo> implements ProjectInfoService {

    @Resource
    private ProjectInfoMapper projectInfoMapper;

    @Override
    public void addProject(String projectName, String description, java.math.BigDecimal budget) {
        if (UserContext.get() == null || UserContext.get().getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "请先登录后再申报项目");
        }
        Long currentUserId = UserContext.get().getId();
        // 1. 构建 ProjectInfo 实体对象
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setProjectName(projectName);
        projectInfo.setDescription(description);
        projectInfo.setBudget(budget);
        projectInfo.setStatus(0); // 0 - 待审批
        projectInfo.setLeaderId(currentUserId);
        projectInfo.setBalance(budget);
        

        // 2. 保存到数据库
        projectInfoMapper.addProject(projectInfo);
    }
}
