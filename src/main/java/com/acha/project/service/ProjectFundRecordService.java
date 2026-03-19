package com.acha.project.service;

import com.acha.project.model.dto.project.fund.FundApplyRequestDTO;
import com.acha.project.model.entity.ProjectFundRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 项目经费报销服务接口
 */
public interface ProjectFundRecordService extends IService<ProjectFundRecord> {

    /**
     * 提交经费报销申请
     *
     * @param requestDTO 申请参数
     * @return 报销记录ID
     */
    Long applyFund(FundApplyRequestDTO requestDTO);

    /**
     * 审核经费报销记录 (手写SQL处理并发扣减)
     * @param requestDTO 审核参数
     * @return 成功与否
     */
    boolean auditFund(com.acha.project.model.dto.project.fund.FundAuditRequestDTO requestDTO);
}