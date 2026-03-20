package com.acha.project.service.impl;

import com.acha.project.common.ErrorCode;
import com.acha.project.common.UserContext;
import com.acha.project.exception.BusinessException;
import com.acha.project.mapper.ProjectInfoMapper;
import com.acha.project.mapper.ProjectMemberMapper;
import com.acha.project.model.dto.project.member.ProjectMemberAddRequestDTO;
import com.acha.project.model.dto.project.member.ProjectMemberRemoveRequestDTO;
import com.acha.project.model.entity.ProjectInfo;
import com.acha.project.model.entity.ProjectMember;
import com.acha.project.model.vo.project.member.ProjectMemberVO;
import com.acha.project.service.ProjectMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectMemberServiceImpl extends ServiceImpl<ProjectMemberMapper, ProjectMember> implements ProjectMemberService {

    private final ProjectInfoMapper projectInfoMapper;

    public ProjectMemberServiceImpl(ProjectInfoMapper projectInfoMapper) {
        this.projectInfoMapper = projectInfoMapper;
    }

    @Override
    public boolean addMember(ProjectMemberAddRequestDTO requestDTO) {
        if (requestDTO == null || requestDTO.getProjectId() == null || requestDTO.getUserId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        Long currentUserId = UserContext.get().getId();
        Long projectId = requestDTO.getProjectId();
        Long userIdToAdd = requestDTO.getUserId();

        // 1. 检查项目是否存在
        ProjectInfo projectInfo = projectInfoMapper.getProjectById(projectId);
        if (projectInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "项目不存在");
        }

        // 2. 权限校验：只有项目负责人有权添加成员
        if (!projectInfo.getLeaderId().equals(currentUserId)) {
            // 这里为了简化，只判断是否为负责人。如果以后有管理员概念，也可以在这里加 admin 判断
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "仅项目负责人可添加团队成员");
        }

        // 3. 检查自己不能把自己加入(负责人本来就有权限，无需再加入成员表，或者允许加也不报错，这里我们做个逻辑统一：负责人不需要当作普通成员加一次)
        if (currentUserId.equals(userIdToAdd)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "项目负责人自动拥有所有权限，无需作为成员添加");
        }

        // 4. 检查该用户是否已经在项目中了（调用刚写的手写 SQL）
        int activeCount = this.baseMapper.countActiveMember(projectId, userIdToAdd);
        if (activeCount > 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该科研人员已存在本团队中，无需重复添加");
        }

        // 5. 构造实体并调用手写的大招 SQL 插入
        ProjectMember member = new ProjectMember();
        member.setProjectId(projectId);
        member.setUserId(userIdToAdd);
        member.setMemberRole(requestDTO.getMemberRole());

        // 使用 ON DUPLICATE KEY UPDATE 的手写SQL处理入库
        int rows = this.baseMapper.insertOrUpdateMember(member);
        return rows > 0;
    }

    @Override
    public boolean removeMember(ProjectMemberRemoveRequestDTO requestDTO) {
        if (requestDTO == null || requestDTO.getProjectId() == null || requestDTO.getUserId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        Long currentUserId = UserContext.get().getId();
        Long projectId = requestDTO.getProjectId();
        Long userIdToRemove = requestDTO.getUserId();

        // 1. 检查项目是否存在
        ProjectInfo projectInfo = projectInfoMapper.getProjectById(projectId);
        if (projectInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "项目不存在");
        }

        // 2. 权限校验
        if (!projectInfo.getLeaderId().equals(currentUserId)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "仅项目负责人可移除项目成员");
        }

        // 3. 不能移除负责人
        if (projectInfo.getLeaderId().equals(userIdToRemove)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不能移除项目负责人");
        }

        // 4. 判断该成员是否真的在项目中
        int activeCount = this.baseMapper.countActiveMember(projectId, userIdToRemove);
        if (activeCount == 0) {
            // 说明不存在或者已经被删除了
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "成员不存在或已被移除");
        }

        // 5. 执行移除（采用软删除 `del_flag = 1`）
        int rows = this.baseMapper.removeMember(projectId, userIdToRemove);
        return rows > 0;
    }

    @Override
    public List<ProjectMemberVO> listMembers(Long projectId) {
        if (projectId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "项目ID为空");
        }

        // 1. 检查项目是否存在（可选，或者直接查）
        ProjectInfo projectInfo = projectInfoMapper.getProjectById(projectId);
        if (projectInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "项目不存在");
        }

        // 2. 只有查询项目的成员或者项目负责人或者自己是系统管理员的逻辑(为了简便，暂不强校验权限，或者如果你需要只能项目的成员可以查看，这里可以加一段校验)
        // 这个按需调整即可

        // 3. 执行连表查询获取列表详情
        return this.baseMapper.listMembers(projectId);
    }
}
