package com.acha.project.service.impl;

import com.acha.project.common.ErrorCode;
import com.acha.project.common.UserContext;
import com.acha.project.exception.BusinessException;
import com.acha.project.mapper.ProjectAuditLogMapper;
import com.acha.project.mapper.ProjectInfoMapper;
import com.acha.project.mapper.ProjectMemberMapper;
import com.acha.project.model.dto.project.audit.ProjectAuditLogQueryRequestDTO;
import com.acha.project.model.dto.user.LoginUserDTO;
import com.acha.project.model.entity.ProjectInfo;
import com.acha.project.model.vo.project.audit.ProjectAuditLogVO;
import com.acha.project.service.ProjectAuditLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProjectAuditLogServiceImpl implements ProjectAuditLogService {

    private final ProjectAuditLogMapper projectAuditLogMapper;
    private final ProjectInfoMapper projectInfoMapper;
    private final ProjectMemberMapper projectMemberMapper;

    public ProjectAuditLogServiceImpl(ProjectAuditLogMapper projectAuditLogMapper,
                                      ProjectInfoMapper projectInfoMapper,
                                      ProjectMemberMapper projectMemberMapper) {
        this.projectAuditLogMapper = projectAuditLogMapper;
        this.projectInfoMapper = projectInfoMapper;
        this.projectMemberMapper = projectMemberMapper;
    }

    @Override
    public Page<ProjectAuditLogVO> pageAuditLogs(ProjectAuditLogQueryRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "查询参数不能为空");
        }

        Long projectId = requestDTO.getProjectId();
        Integer moduleType = requestDTO.getModuleType();
        Long current = requestDTO.getCurrent();
        Long pageSize = requestDTO.getPageSize();

        if (projectId == null || projectId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "projectId 非法");
        }
        if (moduleType == null || (moduleType != 1 && moduleType != 2 && moduleType != 3)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "moduleType 仅支持 1/2/3");
        }

        long safeCurrent = (current == null || current <= 0) ? 1L : current;
        long safePageSize = (pageSize == null || pageSize <= 0) ? 10L : Math.min(pageSize, 100L);

        LoginUserDTO currentUser = UserContext.get();
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        ProjectInfo projectInfo = projectInfoMapper.getProjectById(projectId);
        if (projectInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "项目不存在");
        }

        Long currentUserId = currentUser.getId();
        Integer role = currentUser.getRole();
        boolean isAdmin = role != null && role >= 1;
        boolean isLeader = currentUserId.equals(projectInfo.getLeaderId());

        boolean onlySelfRelated = false;
        if (!isAdmin && !isLeader) {
            int memberCount = projectMemberMapper.countActiveMember(projectId, currentUserId);
            if (memberCount <= 0) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权查看该项目审批日志");
            }
            onlySelfRelated = true;
        }

        long offset = (safeCurrent - 1) * safePageSize;
        long total = projectAuditLogMapper.countAuditLogs(projectId, moduleType, currentUserId, onlySelfRelated);

        List<ProjectAuditLogVO> records = Collections.emptyList();
        if (total > 0) {
            records = projectAuditLogMapper.listAuditLogsByPage(projectId, moduleType, currentUserId, onlySelfRelated, offset, safePageSize);
        }

        Page<ProjectAuditLogVO> page = new Page<>(safeCurrent, safePageSize, total);
        page.setRecords(records);
        return page;
    }
}
