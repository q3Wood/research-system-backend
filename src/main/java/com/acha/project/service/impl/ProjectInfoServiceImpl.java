package com.acha.project.service.impl;

import com.acha.project.model.entity.ProjectInfo;
import com.acha.project.model.entity.ProjectAuditLog;

import org.springframework.stereotype.Service;

import com.acha.project.common.ErrorCode;
import com.acha.project.common.UserContext;
import com.acha.project.exception.BusinessException;
import com.acha.project.mapper.ProjectInfoMapper;
import com.acha.project.service.ProjectInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.acha.project.model.dto.project.info.ProjectAuditRequestDTO;
import com.acha.project.model.dto.project.info.ProjectDeleteRequestDTO;
import com.acha.project.model.dto.project.info.ProjectResubmitRequestDTO;
import com.acha.project.mapper.ProjectAuditLogMapper;
import com.acha.project.model.vo.project.ProjectResubmitVO;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ProjectInfoServiceImpl extends ServiceImpl<ProjectInfoMapper, ProjectInfo> implements ProjectInfoService {

    @Resource
    private ProjectInfoMapper projectInfoMapper;

    @Resource
    private ProjectAuditLogMapper projectAuditLogMapper;

    @Resource
    private com.acha.project.mapper.ProjectMemberMapper projectMemberMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
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

        // 3. 记录初始提交日志
        ProjectAuditLog auditLog = new ProjectAuditLog();
        auditLog.setProjectId(projectInfo.getId());
        auditLog.setModuleType(1); // 1-项目申报
        auditLog.setBusinessId(projectInfo.getId());
        auditLog.setOperatorId(currentUserId);
        auditLog.setAction("提交申请");
        auditLog.setRemark("项目初次申报");
        projectAuditLogMapper.insertAuditLog(auditLog);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditProject(ProjectAuditRequestDTO request) {
        // 1. 获取当前登录用户（审核人）
         if (UserContext.get() == null || UserContext.get().getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        Long operatorId = UserContext.get().getId();

        // 2. 获取项目信息
        ProjectInfo project = projectInfoMapper.getProjectById(request.getProjectId());
        if (project == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "项目不存在");
        }

        // 3. 校验状态流转 (只有 '待审批' 状态的项目才能被审核)
        if (project.getStatus() != 0 && project.getStatus() != 2) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该项目当前状态不可审核");
        }

        // 4. 校验审核结果状态 (校验参数合法性)
        Integer newStatus = request.getAuditStatus();
        if (newStatus != 1 && newStatus != 2) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "审核状态不合法，仅支持通过(1)或驳回(2)");
        }

        // 5. 更新项目状态
        projectInfoMapper.updateProjectStatus(project.getId(), newStatus);
        
        // 5.5 如果审核通过，自动将项目负责人加入到项目成员表
        if (newStatus == 1) {
            com.acha.project.model.entity.ProjectMember member = new com.acha.project.model.entity.ProjectMember();
            member.setProjectId(project.getId());
            member.setUserId(project.getLeaderId());
            member.setMemberRole("项目负责人"); // 设置角色名称
            member.setJoinTime(new java.util.Date()); // 设置加入时间
            // 当需要恢复已退出的成员时，insertOrUpdateMember 中会对 del_flag 和 member_role 进行更新，因此不需要显式设置 delFlag=0（Mapper的SQL层会强制设为0）
            projectMemberMapper.insertOrUpdateMember(member);
        }

        // 6. 记录审核日志
        ProjectAuditLog auditLog = new ProjectAuditLog();
        auditLog.setProjectId(project.getId());
        auditLog.setModuleType(1); // 1-项目申报
        auditLog.setBusinessId(project.getId());
        auditLog.setOperatorId(operatorId);
        
        String action = (newStatus == 1) ? "审核通过" : "审核驳回";
        auditLog.setAction(action);
        auditLog.setRemark(request.getRemark());
        
        projectAuditLogMapper.insertAuditLog(auditLog);
    }

    @Override
    public com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.acha.project.model.vo.project.ProjectInfoVO> pageProjects(
            com.acha.project.model.dto.project.info.ProjectQueryRequestDTO request) {
        
        // 1. 获取当前登录用户
        com.acha.project.model.dto.user.LoginUserDTO currentUser = UserContext.get();
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        // 2. 取出分页参数
        long limit = request.getPageSize();
        long current = request.getCurrent();
        long offset = (current - 1) * limit;

        Long userId = currentUser.getId();
        Integer role = currentUser.getRole();

        // 3. 手写SQL统计满足条件的总数
        long total = projectInfoMapper.countProjects(request, userId, role);

        // 4. 判断是否有数据，有则手写SQL查询记录 (带上了负责人的 real_name)
        java.util.List<com.acha.project.model.vo.project.ProjectInfoVO> pList = new java.util.ArrayList<>();
        if (total > 0) {
            pList = projectInfoMapper.listProjectsByPage(request, userId, role, offset, limit);
        }

        // 5. 组装并返回带分页的 VO 列表
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.acha.project.model.vo.project.ProjectInfoVO> voPage = 
            new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(current, limit, total);
        voPage.setRecords(pList);

        return voPage;
    }

    @Override
    public com.acha.project.model.vo.project.ProjectInfoVO getProjectDetailById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "非法的项目ID");
        }
        com.acha.project.model.vo.project.ProjectInfoVO vo = projectInfoMapper.getProjectVOById(id);
        if (vo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "项目不存在或已被删除");
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteProject(ProjectDeleteRequestDTO request) {
        if (request == null || request.getProjectId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不能为空");
        }
        if (UserContext.get() == null || UserContext.get().getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        Long currentUserId = UserContext.get().getId();
        Integer role = UserContext.get().getRole();
        boolean isAdmin = role != null && role >= 1;

        ProjectInfo project = projectInfoMapper.getProjectById(request.getProjectId());
        if (project == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "项目不存在");
        }

        boolean isLeader = currentUserId.equals(project.getLeaderId());
        if (!isAdmin && !isLeader) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "仅项目负责人或管理员可删除项目");
        }

        Integer status = project.getStatus();
        if (!Integer.valueOf(0).equals(status) && !Integer.valueOf(2).equals(status)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "仅待审或驳回状态的项目可删除");
        }

        int rows = projectInfoMapper.logicalDeleteProject(project.getId());
        if (rows <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "项目删除失败，请稍后重试");
        }

        ProjectAuditLog auditLog = new ProjectAuditLog();
        auditLog.setProjectId(project.getId());
        auditLog.setModuleType(1);
        auditLog.setBusinessId(project.getId());
        auditLog.setOperatorId(currentUserId);
        auditLog.setAction("项目删除");
        auditLog.setRemark(request.getDeleteReason());
        projectAuditLogMapper.insertAuditLog(auditLog);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProjectResubmitVO resubmitProject(ProjectResubmitRequestDTO request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不能为空");
        }
        if (UserContext.get() == null || UserContext.get().getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        Long currentUserId = UserContext.get().getId();
        Integer role = UserContext.get().getRole();
        boolean isAdmin = role != null && role >= 1;

        ProjectInfo project = projectInfoMapper.getProjectById(request.getProjectId());
        if (project == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "项目不存在");
        }

        boolean isLeader = currentUserId.equals(project.getLeaderId());
        if (!isAdmin && !isLeader) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限操作该项目");
        }

        if (!Integer.valueOf(2).equals(project.getStatus())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "当前项目状态不允许重提");
        }

        int rows = projectInfoMapper.resubmitProject(
                request.getProjectId(),
                request.getProjectName(),
                request.getDescription(),
                request.getBudget()
        );
        if (rows <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "重提失败，请稍后重试");
        }

        ProjectAuditLog auditLog = new ProjectAuditLog();
        auditLog.setProjectId(project.getId());
        auditLog.setModuleType(1);
        auditLog.setBusinessId(project.getId());
        auditLog.setOperatorId(currentUserId);
        auditLog.setAction("项目重提");
        auditLog.setRemark(request.getResubmitReason());
        projectAuditLogMapper.insertAuditLog(auditLog);

        ProjectResubmitVO vo = new ProjectResubmitVO();
        vo.setProjectId(project.getId());
        vo.setStatus(0);
        vo.setStatusText("申报待审");
        return vo;
    }
}