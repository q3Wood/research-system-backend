package com.acha.project.service;

import com.acha.project.model.entity.User;
import com.acha.project.model.dto.user.UserUpdateMyRequestDTO;
import com.acha.project.model.vo.user.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户服务接口
 * 继承 IService<User> 后，自动拥有了基础的增删改查方法
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param username   账号
     * @param password  密码
     * @param checkPassword 校验密码
     * @return 新用户ID
     */
    long userRegister(String username, String password, String checkPassword);

    /**
     * 用户登录
     *
     * @param username  账号
     * @param password 密码
     * @return 脱敏后的用户信息
     */
    UserVO userLogin(String username, String password);

    /**
     * 获取当前登录用户
     * @param request 请求对象
     * @return 脱敏后的用户信息
     */
    UserVO getLoginUser();

    /**
     * 用户注销
     * @param token Authorization header
     * @return 是否成功
     */
    boolean userLogout(String token);

    /**
     * 修改密码
     *
     * @param oldPassword   旧密码
     * @param newPassword   新密码
     * @param checkPassword 确认新密码
     * @return 是否成功
     */
    boolean updatePassword(String oldPassword, String newPassword, String checkPassword);

    /**
     * 更新个人信息
     *
     * @param request 包含要修改的字段的 DTO
     * @return 是否成功
     */
    boolean updateUserInfo(UserUpdateMyRequestDTO request);

    /**
     * 更新用户角色
     *
     * @param id   用户ID
     * @param role 新角色 (0-科研人员, 1-普通管理员, 2-超级管理员)
     * @return 是否成功
     */
    boolean updateUserRole(Long id, Integer role);

    /**
     * 获取所有用户列表 (仅管理员)
     * @return 用户VO列表
     */
    java.util.List<UserVO> listAllUsers();
}