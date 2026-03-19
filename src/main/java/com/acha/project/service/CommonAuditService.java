package com.acha.project.service;

import com.acha.project.model.dto.common.CommonAuditRequestDTO;

/**
 * 统一审批服务接口
 */
public interface CommonAuditService {

    /**
     * 统一审批逻辑处理
     * @param requestDTO 审批参数
     * @return 是否成功
     */
    boolean audit(CommonAuditRequestDTO requestDTO);
}
