package com.acha.project.service.impl;

import com.acha.project.common.ErrorCode;
import com.acha.project.common.UserContext;
import com.acha.project.exception.BusinessException;
import com.acha.project.mapper.ProjectAchievementMapper;
import com.acha.project.mapper.ProjectAuditLogMapper;
import com.acha.project.mapper.ProjectFundRecordMapper;
import com.acha.project.mapper.ProjectInfoMapper;
import com.acha.project.model.dto.common.CommonAuditRequestDTO;
import com.acha.project.model.entity.ProjectAchievement;
import com.acha.project.model.entity.ProjectAuditLog;
import com.acha.project.model.entity.ProjectFundRecord;
import com.acha.project.model.entity.ProjectInfo;
import com.acha.project.service.CommonAuditService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CommonAuditServiceImpl implements CommonAuditService {

    private final ProjectInfoMapper projectInfoMapper;
    private final ProjectFundRecordMapper projectFundRecordMapper;
    private final ProjectAchievementMapper projectAchievementMapper;
    private final ProjectAuditLogMapper projectAuditLogMapper;

    public CommonAuditServiceImpl(
            ProjectInfoMapper projectInfoMapper,
            ProjectFundRecordMapper projectFundRecordMapper,
            ProjectAchievementMapper projectAchievementMapper,
            ProjectAuditLogMapper projectAuditLogMapper) {
        this.projectInfoMapper = projectInfoMapper;
        this.projectFundRecordMapper = projectFundRecordMapper;
        this.projectAchievementMapper = projectAchievementMapper;
        this.projectAuditLogMapper = projectAuditLogMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean audit(CommonAuditRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "审批参数为空");
        }

        Long businessId = requestDTO.getBusinessId();
        Integer moduleType = requestDTO.getModuleType();
        Integer auditStatus = requestDTO.getAuditStatus();
        Long currentUserId = UserContext.get().getId();
        Long projectId = null;
        String actionName = auditStatus == 1 ? "审批通过" : "审批驳回";
        
        // 核心路由逻辑，根据模块分别去处理不同表的状态机
        switch (moduleType) {
            case 1:
                // 1 - 项目审批
                ProjectInfo projectInfo = projectInfoMapper.getProjectById(businessId);
                if (projectInfo == null) {
                    throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "项目不存在");
                }
                projectId = projectInfo.getId();
                
                // 写手工SQL更新状态
                int pRows = projectInfoMapper.updateProjectStatus(businessId, auditStatus);
                if (pRows <= 0) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "项目状态更新失败");
                }
                break;

            case 2:
                // 2 - 经费审批
                ProjectFundRecord fundRecord = projectFundRecordMapper.getFundRecordById(businessId);
                if (fundRecord == null) {
                    throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "报销记录不存在");
                }
                if (fundRecord.getStatus() != 0) {
                    throw new BusinessException(ErrorCode.OPERATION_ERROR, "该报销记录已处理，请勿重复审批");
                }
                projectId = fundRecord.getProjectId();

                // 权限校验：通常经费需要根据配置查当前人是否是负责人/管理员，这里简化为 leader_id = currentUserId 校验
                Long leaderId = projectFundRecordMapper.getProjectLeaderIdByFundId(businessId);
                if (leaderId == null || !leaderId.equals(currentUserId)) {
                    // 如果有管理员角色判断也可以放在这里，这里做简化
                    throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "仅项目负责人可以审核经费报销");
                }

                if (auditStatus == 1) { // 审核通过
                    BigDecimal amount = fundRecord.getAmount();
                    // 并发扣款的SQL(内部带 WHERE balance >= amount)
                    int deductRows = projectInfoMapper.deductProjectBalance(projectId, amount);
                    if (deductRows <= 0) {
                        throw new BusinessException(ErrorCode.OPERATION_ERROR, "项目可用金额不足或项目不存在");
                    }
                }

                // 统一写手工SQL更新报销状态
                int fRows = projectFundRecordMapper.updateFundRecordStatus(businessId, auditStatus);
                if (fRows <= 0) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "经费状态更新失败");
                }
                break;

            case 3:
                // 3 - 成果审批
                ProjectAchievement achievement = projectAchievementMapper.getAchievementById(businessId);
                if (achievement == null) {
                    throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "成果不存在");
                }
                if (achievement.getStatus() != 0) {
                    throw new BusinessException(ErrorCode.OPERATION_ERROR, "该成果已被处理");
                }
                projectId = achievement.getProjectId();

                // 更新成果状态 (手写SQL), 注意这里只存 0-待验收, 1-已通过, 2-驳回, 可以沿用 status=1/2
                int aRows = projectAchievementMapper.updateAchievementStatus(businessId, auditStatus);
                if (aRows <= 0) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "成果状态更新失败");
                }
                break;

            default:
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "未知的模块类型");
        }

        // 统一插入到公共审批日志表
        ProjectAuditLog auditLog = new ProjectAuditLog();
        auditLog.setProjectId(projectId);
        auditLog.setModuleType(moduleType);
        auditLog.setBusinessId(businessId);
        auditLog.setOperatorId(currentUserId);
        auditLog.setAction(actionName);
        auditLog.setRemark(requestDTO.getRemark());
        projectAuditLogMapper.insertAuditLog(auditLog);

        return true;
    }
}
