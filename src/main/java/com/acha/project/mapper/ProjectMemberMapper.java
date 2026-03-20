package com.acha.project.mapper;

import com.acha.project.model.entity.ProjectMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 项目团队成员表数据库操作接口
 */
public interface ProjectMemberMapper extends BaseMapper<ProjectMember> {

    /**
     * 手写SQL：新增或恢复项目成员（利用 ON DUPLICATE KEY UPDATE 处理曾被软删除的情况）
     * @param member 成员实体
     * @return 影响行数
     */
    int insertOrUpdateMember(ProjectMember member);

    /**
     * 手写SQL：查询某个成员是否已经在项目中(且未退组)
     */
    int countActiveMember(@org.apache.ibatis.annotations.Param("projectId") Long projectId, @org.apache.ibatis.annotations.Param("userId") Long userId);

    /**
     * 手写SQL：移除项目成员（软删除）
     */
    int removeMember(@org.apache.ibatis.annotations.Param("projectId") Long projectId, @org.apache.ibatis.annotations.Param("userId") Long userId);

    /**
     * 手写SQL：联表查询某个项目下的所有活跃成员
     */
    java.util.List<com.acha.project.model.vo.project.member.ProjectMemberVO> listMembers(@org.apache.ibatis.annotations.Param("projectId") Long projectId);
}
