package com.acha.project.controller;

import com.acha.project.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 项目成员管理接口
 */
@RestController
@RequestMapping("/project/member")
@Slf4j
@Tag(name = "项目管理-团队成员", description = "项目人员组建、邀请、移除接口")
public class ProjectMemberController {

    private final com.acha.project.service.ProjectMemberService projectMemberService;

    public ProjectMemberController(com.acha.project.service.ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    @GetMapping("/test")
    @Operation(summary = "测试接口")
    public BaseResponse<String> test() {
        return BaseResponse.success("ProjectMemberController 运行正常");
    }

    @PostMapping("/add")
    @Operation(summary = "添加成员入组")
    public BaseResponse<Boolean> addMember(@org.springframework.validation.annotation.Validated @RequestBody com.acha.project.model.dto.project.member.ProjectMemberAddRequestDTO requestDTO) {
        boolean result = projectMemberService.addMember(requestDTO);
        return BaseResponse.success(result);
    }

    @PostMapping("/remove")
    @Operation(summary = "移除项目成员")
    public BaseResponse<Boolean> removeMember(@org.springframework.validation.annotation.Validated @RequestBody com.acha.project.model.dto.project.member.ProjectMemberRemoveRequestDTO requestDTO) {
        boolean result = projectMemberService.removeMember(requestDTO);
        return BaseResponse.success(result);
    }

    @GetMapping("/list")
    @Operation(summary = "获取项目下所有成员列表")
    public BaseResponse<java.util.List<com.acha.project.model.vo.project.member.ProjectMemberVO>> listMembers(@RequestParam("projectId") Long projectId) {
        java.util.List<com.acha.project.model.vo.project.member.ProjectMemberVO> result = projectMemberService.listMembers(projectId);
        return BaseResponse.success(result);
    }

    // TODO: 4. 修改成员角色 (Post)
}
