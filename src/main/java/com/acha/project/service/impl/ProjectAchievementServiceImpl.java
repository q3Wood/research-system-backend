package com.acha.project.service.impl;

import com.acha.project.common.ErrorCode;
import com.acha.project.common.UserContext;
import com.acha.project.exception.BusinessException;
import com.acha.project.mapper.ProjectAchievementMapper;
import com.acha.project.model.dto.project.achievement.AchievementAddRequestDTO;
import com.acha.project.model.entity.ProjectAchievement;
import com.acha.project.model.entity.ProjectAuditLog;
import com.acha.project.mapper.ProjectAuditLogMapper;
import com.acha.project.model.dto.user.LoginUserDTO;
import com.acha.project.service.ProjectAchievementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectAchievementServiceImpl extends ServiceImpl<ProjectAchievementMapper, ProjectAchievement>
        implements ProjectAchievementService {

    private final ProjectAuditLogMapper projectAuditLogMapper;

    public ProjectAchievementServiceImpl(ProjectAuditLogMapper projectAuditLogMapper) {
        this.projectAuditLogMapper = projectAuditLogMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long submitAchievement(AchievementAddRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        // 1. 获取当前登录用户
        Long currentUserId = UserContext.get().getId();

        // 2. 权限与前置校验（手写SQL）：是否存在该项目且该用户是成员/负责人，且项目处于执行中(1)
        Integer projectStatus = this.baseMapper.checkProjectStatusForMember(requestDTO.getProjectId(), currentUserId);
        if (projectStatus == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "您不是该项目成员或该项目不存在");
        }
        if (projectStatus != 1) { // 假设 1-执行中
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "只有执行中的项目才能提交成果");
        }

        // 3. 构造实体并拷贝属性
        ProjectAchievement achievement = new ProjectAchievement();
        BeanUtils.copyProperties(requestDTO, achievement);
        
        // 3. 补充系统字段
        achievement.setSubmitterId(currentUserId);
        // 0-待验收 (数据库可能也有默认值，但代码里设置更稳妥)
        achievement.setStatus(0);

        // 4. 插入数据库
        boolean result = this.save(achievement);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "成果提交失败，数据库异常");
        }

        // 5. 【新增逻辑】插入流水表记录
        ProjectAuditLog auditLog = new ProjectAuditLog();
        auditLog.setProjectId(requestDTO.getProjectId());
        auditLog.setModuleType(3); // 3-成果验收
        auditLog.setBusinessId(achievement.getId());
        auditLog.setOperatorId(currentUserId);
        auditLog.setAction("提交成果");
        auditLog.setRemark("成果初次提交待审");
        projectAuditLogMapper.insertAuditLog(auditLog);

        return achievement.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean auditAchievement(com.acha.project.model.dto.project.achievement.AchievementAuditRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        Long achievementId = requestDTO.getId();
        Integer status = requestDTO.getStatus();

        if (status != 1 && status != 2) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "非法的审核状态");
        }

        // 1. 检查成果是否存在
        ProjectAchievement achievement = this.getById(achievementId);
        if (achievement == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "成果不存在");
        }

        // 2. 判断成果状态是否允许审核 (必须是 0 待验收)
        if (achievement.getStatus() != 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该成果已被审核，不能重复操作");
        }

        // 3. 权限校验：获取当前用户
        LoginUserDTO currentUser = UserContext.get();
        Long currentUserId = currentUser.getId();
        Integer role = currentUser.getRole();

        // 4. 找出项目的负责人
        Long leaderId = this.baseMapper.getProjectLeaderIdByAchievementId(achievementId);
        if (leaderId == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "成果对应的项目数据异常");
        }

        // 5. 判断权限：只有该项目的负责人，或者角色 >= 1 (管理员) 的人才可以验收
        boolean hasAuth = currentUserId.equals(leaderId) || (role != null && role >= 1);
        if (!hasAuth) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "您不是该项目负责人或管理员，无权验收");
        }

        // 6. 更新状态
        achievement.setStatus(status);
        boolean updateResult = this.updateById(achievement);
        
        if (!updateResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "审核失败");
        }

        // 7. 【新增逻辑】写入审计流水表
        String actionName = (status == 1) ? "验收通过" : "验收打回";
        ProjectAuditLog auditLog = new ProjectAuditLog();
        // ★注意：流水表需要 projectId，所以我们要通过查出的成果对象找到它的 projectId
        auditLog.setProjectId(achievement.getProjectId());
        auditLog.setModuleType(3); // 3-成果验收
        auditLog.setBusinessId(achievementId);
        auditLog.setOperatorId(currentUserId);
        auditLog.setAction(actionName);
        auditLog.setRemark(requestDTO.getRemark());
        projectAuditLogMapper.insertAuditLog(auditLog);

        return true;
    }

    @Override
    public com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.acha.project.model.vo.ProjectAchievementVO> listAchievementsByPage(
            Long projectId, com.acha.project.common.PageRequest request) {
        
        if (projectId == null || projectId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "项目ID非法");
        }

        // 1. 获取分页参数 (手写 SQL 分页，通过 LIMIT 和 OFFSET 实现)
        long current = request.getCurrent();
        long size = request.getPageSize();
        long offset = (current - 1) * size;

        // 2. 先查是否存在数据总和，提高性能
        long total = this.baseMapper.countAchievements(projectId);
        
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.acha.project.model.vo.ProjectAchievementVO> pageResult 
            = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(current, size, total);
        
        if (total == 0) {
            return pageResult; // 没有记录直接返回空页
        }

        // 3. 查出当前页的明细 (包含通过 left join user 获取的提交人真实姓名)
        java.util.List<com.acha.project.model.vo.ProjectAchievementVO> records = this.baseMapper.listAchievementsWithSubmitterName(projectId, offset, size);
        pageResult.setRecords(records);

        return pageResult;
    }
}
