package com.acha.project.mapper;

import com.acha.project.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户数据库操作接口
 * 继承 BaseMapper 后，自动拥有增删改查能力
 * 具体的 SQL 实现已迁移至 src/main/resources/mapper/UserMapper.xml
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名统计数量
     * @param username 用户名
     * @return 数量
     */
    Long countByUsername(@Param("username") String username);

    /**
     * 插入用户
     * @param user 用户对象
     * @return 受影响行数
     */
    int insertUser(User user);

    /**
     * 根据用户名查询并过滤逻辑删除
     * @param username 用户名
     * @return 用户对象
     */
    User selectByUsername(@Param("username") String username);

    /**
     * 根据ID查询并过滤逻辑删除
     * @param id 用户ID
     * @return 用户对象
     */
    User selectUserById(@Param("id") Long id);

    /**
     * 动态更新用户信息
     * @param user 用户对象
     * @return 受影响行数
     */
    int updateUser(User user);

    /**
     * 查询所有用户
     * @return 用户列表
     */
    java.util.List<User> selectAllUsers();

    /**
     * 查询满足条件的用户总数
     * @param requestDTO 筛选条件
     * @return 数量
     */
    long countUsers(@Param("requestDTO") com.acha.project.model.dto.user.UserQueryRequestDTO requestDTO);

    /**
     * 分页查询满足条件的用户
     * @param requestDTO 筛选条件
     * @param offset 偏移量
     * @param limit 每页数量
     * @return 用户列表
     */
    java.util.List<User> listUsersByPage(@Param("requestDTO") com.acha.project.model.dto.user.UserQueryRequestDTO requestDTO, @Param("offset") long offset, @Param("limit") long limit);
}