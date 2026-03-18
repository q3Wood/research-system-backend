package com.acha.project.service;

import com.acha.project.model.dto.project.achievement.AchievementAddRequestDTO;
import com.acha.project.model.entity.ProjectAchievement;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProjectAchievementService extends IService<ProjectAchievement> {

    /**
     * 提交成果
     *
     * @param requestDTO 提交成果信息
     * @return 提交成功返回成果的id
     */
    Long submitAchievement(AchievementAddRequestDTO requestDTO);

    /**
     * 审核验收成果
     *
     * @param requestDTO 审核信息
     * @return 审核是否成功
     */
    boolean auditAchievement(com.acha.project.model.dto.project.achievement.AchievementAuditRequestDTO requestDTO);

    /**
     * 分页查询某项目下的所有成果 (联合查询提交人姓名)
     *
     * @param projectId 项目ID
     * @param request 分页请求及其它查询条件
     * @return 包含成果列表的分页结果
     */
    com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.acha.project.model.vo.ProjectAchievementVO> listAchievementsByPage(
            Long projectId, com.acha.project.common.PageRequest request);
}
