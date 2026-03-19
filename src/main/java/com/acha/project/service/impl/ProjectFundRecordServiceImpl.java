package com.acha.project.service.impl;

import com.acha.project.common.ErrorCode;
import com.acha.project.common.UserContext;
import com.acha.project.exception.BusinessException;
import com.acha.project.mapper.ProjectAuditLogMapper;
import com.acha.project.mapper.ProjectFundRecordMapper;
import com.acha.project.mapper.ProjectInfoMapper;
import com.acha.project.model.dto.project.fund.FundApplyRequestDTO;
import com.acha.project.model.entity.ProjectAuditLog;
import com.acha.project.model.entity.ProjectFundRecord;
import com.acha.project.model.entity.ProjectInfo;
import com.acha.project.service.ProjectFundRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectFundRecordServiceImpl extends ServiceImpl<ProjectFundRecordMapper, ProjectFundRecord> 
    implements ProjectFundRecordService {

    private final ProjectInfoMapper projectInfoMapper;
    private final ProjectAuditLogMapper projectAuditLogMapper;

    public ProjectFundRecordServiceImpl(ProjectInfoMapper projectInfoMapper, ProjectAuditLogMapper projectAuditLogMapper) {
        this.projectInfoMapper = projectInfoMapper;
        this.projectAuditLogMapper = projectAuditLogMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long applyFund(FundApplyRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "报销参数为空");
        }

        Long projectId = requestDTO.getProjectId();
        Long currentUserId = UserContext.get().getId();

        // 1. 检查项目是否存在以及状态是否处于"执行中"(1)
        ProjectInfo projectInfo = projectInfoMapper.selectById(projectId);
        if (projectInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "项目不存在");
        }
        if (projectInfo.getStatus() != 1) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "项目未处于执行阶段，无法报销经费");
        }

        // 2. 检查余额是否充足 (防止明明没钱了还提交一大笔报销申请)
        if (projectInfo.getBalance().compareTo(requestDTO.getAmount()) < 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "项目可用余额不足，申请金额超过当前余额");
        }

        // TODO: 前期假设除了负责人，只要知道 projectId 就能报销。
        // 如果严格一点，这里可以复用 ProjectAchievementMapper 的 checkProjectStatusForMember 判断，必须是项目组成员才能把钱花出去。

        // 3. 构造报销流水记录
        ProjectFundRecord fundRecord = new ProjectFundRecord();
        BeanUtils.copyProperties(requestDTO, fundRecord);
        fundRecord.setApplicantId(currentUserId);
        fundRecord.setStatus(0); // 0-待审核

        // 改用手写SQL插入报销申请记录
        int rows = this.baseMapper.insertFundRecord(fundRecord);
        if (rows <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "提交报销申请失败");
        }

        // 4. 插入通用审核流转日志
        ProjectAuditLog auditLog = new ProjectAuditLog();
        auditLog.setProjectId(projectId);
        auditLog.setModuleType(2); // 2-经费报销
        auditLog.setBusinessId(fundRecord.getId());
        auditLog.setOperatorId(currentUserId);
        auditLog.setAction("提交报销申请");
        auditLog.setRemark("申请金额: " + requestDTO.getAmount() + "元，用途: " + requestDTO.getUsageDesc());
        projectAuditLogMapper.insertAuditLog(auditLog);

        return fundRecord.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean auditFund(com.acha.project.model.dto.project.fund.FundAuditRequestDTO requestDTO) {
        if (requestDTO == null || requestDTO.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "审批参数为空");
        }

        Long recordId = requestDTO.getId();
        Integer auditStatus = requestDTO.getStatus(); // 1-通过, 2-驳回
        Long currentUserId = UserContext.get().getId();

        ProjectFundRecord fundRecord = this.baseMapper.getFundRecordById(recordId);
        if (fundRecord == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "报销记录不存在");
        }
        if (fundRecord.getStatus() != 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该报销记录已处理，请勿重复审批");
        }

        Long projectId = fundRecord.getProjectId();

        // 校验权限 (只有项目负责人能审批)
        Long leaderId = this.baseMapper.getProjectLeaderIdByFundId(recordId);
        if (leaderId == null || !leaderId.equals(currentUserId)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "仅项目负责人可以审核经费报销");
        }

        if (auditStatus == 1) { // 审核通过，并发扣款
            java.math.BigDecimal amount = fundRecord.getAmount();
            // 手写SQL同时兼顾并发查询和更新扣款: UPDATE project_info SET balance = balance - #{amount} WHERE balance >= #{amount}
            int deductRows = projectInfoMapper.deductProjectBalance(projectId, amount);
            if (deductRows <= 0) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "项目可用金额不足或状态异常，扣费失败");
            }
        }

        // 手写SQL更新报销单状态
        int updateRows = this.baseMapper.updateFundRecordStatus(recordId, auditStatus);
        if (updateRows <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "报销状态更新失败");
        }

        // 插入审计日志
        ProjectAuditLog auditLog = new ProjectAuditLog();
        auditLog.setProjectId(projectId);
        auditLog.setModuleType(2); // 经费
        auditLog.setBusinessId(recordId);
        auditLog.setOperatorId(currentUserId);
        auditLog.setAction(auditStatus == 1 ? "经费审批通过" : "经费审批驳回");
        auditLog.setRemark(requestDTO.getRemark());
        projectAuditLogMapper.insertAuditLog(auditLog);

        return true;
    }
}
