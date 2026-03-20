package com.acha.project.service;

import com.acha.project.model.dto.project.member.ProjectMemberAddRequestDTO;
import com.acha.project.model.dto.project.member.ProjectMemberRemoveRequestDTO;
import com.acha.project.model.entity.ProjectMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 项目成员管理服务接口
 */
public interface ProjectMemberService extends IService<ProjectMember> {

    /**
     * 手写SQL：新增成员到项目中
     *
     * @param requestDTO 添加成员参数
     * @return 成功与否
     */
    boolean addMember(ProjectMemberAddRequestDTO requestDTO);

    /**
     * 手写SQL：移除项目成员
     *
     * @param requestDTO 成员移除参数
     * @return 成功与否
     */
    boolean removeMember(ProjectMemberRemoveRequestDTO requestDTO);

    /**
     * 手写SQL：获取项目成员列表
     *
     * @param projectId 项目ID
     * @return 成员列表
     */
    java.util.List<com.acha.project.model.vo.project.member.ProjectMemberVO> listMembers(Long projectId);
}
