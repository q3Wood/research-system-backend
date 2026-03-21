package com.acha.project.service;

import com.acha.project.model.dto.project.info.ProjectAuditRequestDTO;
import com.acha.project.model.dto.project.info.ProjectDeleteRequestDTO;
import com.acha.project.model.dto.project.info.ProjectResubmitRequestDTO;
import com.acha.project.model.entity.ProjectInfo;
import com.acha.project.model.vo.project.ProjectResubmitVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProjectInfoService extends IService<ProjectInfo>{

    /**
     * 创建科研项目
     * @param projectName 项目名称
     * @param description 项目描述
     * @param budget 项目预算
     * @return 创建成功的项目信息
     */
    void addProject(String projectName, String description, java.math.BigDecimal budget);

    /**
     * 审核科研项目
     * @param request 审核请求体
     */
    void auditProject(ProjectAuditRequestDTO request);
    
    /**
     * 分页查询项目列表
     * @param request 筛选条件
     * @return 包含项目信息的VO分页对象
     */
    com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.acha.project.model.vo.project.ProjectInfoVO> pageProjects(
        com.acha.project.model.dto.project.info.ProjectQueryRequestDTO request);

    /**
     * 获取单个项目详情
     * @param id 项目ID
     * @return 项目详情VO
     */
    com.acha.project.model.vo.project.ProjectInfoVO getProjectDetailById(Long id);

    /**
     * 删除项目（逻辑删除）
     */
    boolean deleteProject(ProjectDeleteRequestDTO request);

    /**
     * 项目驳回后修改重提（更新原记录）
     */
    ProjectResubmitVO resubmitProject(ProjectResubmitRequestDTO request);
}

